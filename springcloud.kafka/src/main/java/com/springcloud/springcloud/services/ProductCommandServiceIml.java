package com.springcloud.springcloud.services;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.springcloud.springcloud.messaging.ReplyInbox;
import com.springcloud.springcloud.models.Command;
import com.springcloud.springcloud.models.Repply;
import com.springcloud.springcloud.models.dto.ProductDto;

@Service
public class ProductCommandServiceIml implements ProductCommandService {

    // El StreamBridge funciona como nuestro Event Dispatcher o Bus en Laravel
    private final StreamBridge bridge;
    private final ReplyInbox replyInbox;
    private static final Logger logger = LoggerFactory.getLogger(ProductCommandServiceIml.class);

    public ProductCommandServiceIml(StreamBridge bridge, ReplyInbox replyInbox) {
        this.bridge = bridge;
        this.replyInbox = replyInbox;
    }

    @Override
    public Repply<?> sendCreateAndAwait(ProductDto dto, Duration timeout) {
        // Creamos el comando (equivale a instanciar un Job o Event en Laravel)
        Command<ProductDto> cmd = new Command<>("CREATE", null, dto);
        String correlationId = UUID.randomUUID().toString();
        logger.info("API Products Client Creating product with correlationId {}", correlationId);
        CompletableFuture<Repply<?>> future = replyInbox.register(correlationId);
        Message<Command<ProductDto>> msg = MessageBuilder.withPayload(cmd)
        .setHeader("correlationId", correlationId).build();

        
        
        // Enviamos al canal definido como "commands-out-0"
        // '0' es el estándar para el primer binding de salida (productor)
        boolean sent = this.bridge.send("commands-out-0", msg);

        // Validación de éxito similar a checar si un Job fue despachado
        if (!sent) {
            throw new IllegalStateException("No se pudo enviar el comando a kafka");
        }
        try {
            return future.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted while waiting for reply", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Error processing command reply", e.getCause());
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout waiting for reply", e);
        }

    }
}