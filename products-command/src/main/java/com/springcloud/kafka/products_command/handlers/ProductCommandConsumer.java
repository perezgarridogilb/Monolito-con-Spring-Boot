package com.springcloud.kafka.products_command.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.springcloud.kafka.products_command.models.Command;
import com.springcloud.kafka.products_command.models.CommandType;
import com.springcloud.kafka.products_command.models.Repply;
import com.springcloud.kafka.products_command.models.RepplyStatus;
import com.springcloud.kafka.products_command.models.dto.ProductDto;
import com.springcloud.kafka.products_command.services.ProductService;

import java.util.function.Consumer;
import java.util.function.Function;

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
            Repply<?> reply;
            switch (cmd.type()) {
                case CommandType.CREATE -> {
                    if(cmd.body() == null) {
                        log.warn("Create Empty body");
                        reply = new Repply<>(RepplyStatus.ERROR, "Create Empty body", null);
                    }
                    ProductDto productSave = service.create(cmd.body());

                    log.info("Creating product name={}, price={}", productSave.name(), productSave.price());
                    reply = new Repply<>(RepplyStatus.SUCCESS, "Create product name", productSave);
                }
                case CommandType.READ -> {
                    if(cmd.id() == null) {
                        log.warn("Id is required");
                        reply = new Repply<>(RepplyStatus.ERROR, "Id is required", null);
                    }
                    ProductDto dto = service.findById(cmd.id());
                    reply = (dto == null)?
                            new Repply<>(RepplyStatus.ERROR, "Product not found", null):
                            new Repply<>(RepplyStatus.SUCCESS, "Read product name", dto);
                    log.info("Reading product by id");
                }
                case CommandType.READ_ALL -> {
                    reply = new Repply<>(RepplyStatus.SUCCESS, "Read all products", service.findAll());
                    log.info("Reading all products");
                }
                case CommandType.UPDATE -> {
                    if(cmd.body() == null || cmd.id() == null) {
                        log.warn("Id and body is required");
                        reply = new Repply<>(RepplyStatus.ERROR, "Id and body is required", null);
                    }
                    ProductDto dto = service.findById(cmd.id());

                    if(dto != null) {
                        reply = new Repply<>(RepplyStatus.SUCCESS, "Update product name", dto);
                        log.info("Updating product name={}, price={}", dto.name(), dto.price());
                    } else  {
                        reply = new Repply<>(RepplyStatus.ERROR, "Product not found", null);
                        log.info("Product not found, null dto");
                    }
                }
                case CommandType.DELETE -> {
                    if(cmd.id() == null) {
                        log.warn("Id is required");
                        reply = new Repply<>(RepplyStatus.ERROR, "Id is required", null);
                    }
                    boolean result = service.delete(cmd.id());
                    reply = (result)? new Repply<>(RepplyStatus.SUCCESS, "Deleting Product", "deleted"):
                            new Repply<>(RepplyStatus.ERROR, "Product not found", null);

                    log.info("Deleting product");
                }
                default -> {
                    log.warn("Unknown command type={}", cmd.type());
                    reply = new Repply<>(RepplyStatus.ERROR, "Unknown command type", null);
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
