package com.springcloud.kafka.products_command.handlers;

import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.springcloud.kafka.products_command.models.Command;
import com.springcloud.kafka.products_command.models.Repply;
import com.springcloud.kafka.products_command.models.dto.ProductDto;
import com.springcloud.kafka.products_command.services.ProductService;

@Configuration
public class ProductCommandConsumer {
    private static final Logger log = LoggerFactory.getLogger(ProductCommandConsumer.class);

    private final ProductService service;

    public ProductCommandConsumer(ProductService productService) {
        this.service = productService;
    }

    @Bean
    public Function<Message<Command<ProductDto>>, Message<Repply<?>>> handleCommands() {
        return msg -> {
            Command<ProductDto> cmd = msg.getPayload();
            String type = cmd.type() == null ? "" : cmd.type().toUpperCase();
            Repply<?> reply;
            switch (type) {
                case "CREATE" -> {
                    if(cmd.body() == null) {
                        log.warn("Create Empty body");
                        reply = new Repply<>("ERROR", "Create Empty body", null);
                    }
                    ProductDto productSave = service.create(cmd.body());

                    log.info("Creating product name={}, price={}", productSave.name(), productSave.price());
                    reply = new Repply<>("SUCCESS", "Create product name", productSave);
                }
                // case "READ" -> log.info("Reading product by id");
                // case "READ_ALL" -> log.info("Reading all products");
                // case "UPDATE" -> log.info("Updating product name={}, price={}", null, null);
                // case "DELETE" -> log.info("Deleting product");
                default -> {
                    log.warn("Unknown command type={}", type);
                    reply = new Repply<>("ERROR", "Unknown command type", null);
                }
            }
            String correlationId = msg.getHeaders().get("correlationId", String.class);
            log.info("Recibiendo CorrelationId={}", correlationId);

            MessageBuilder<Repply<?>> out = MessageBuilder.withPayload(reply);
            if(correlationId != null) {
                out.setHeader("correlationId", correlationId);
            }
            
            return out.build();
        };
    }
}
