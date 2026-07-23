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
    public Function<Message<Command<ProductDto>>, Message<Repply<Object>>> handleCommands() {
        return msg -> {

            String correlationId = msg.getHeaders().get("correlationId", String.class);
            log.info("Recibiendo CorrelationId={}", correlationId);
            if (correlationId == null || correlationId.isBlank()) {
                return MessageBuilder
                        .withPayload(new Repply<>(RepplyStatus.ERROR, "Missing correlationId", null))
                        .build();
            }

            Command<ProductDto> cmd = msg.getPayload();
            Repply<Object> reply = switch (cmd.type()) {
                case CommandType.CREATE -> {
                    if(cmd.body() == null) {
                        log.warn("Create Empty body");
                        yield new Repply<>(RepplyStatus.ERROR, "Create Empty body", null);
                    }
                    ProductDto productSave = service.create(cmd.body());

                    log.info("Creating product name={}, price={}", productSave.name(), productSave.price());
                    yield new Repply<>(RepplyStatus.SUCCESS, "Create product name", productSave);
                }
                case CommandType.READ -> {
                    if (cmd.id() == null) {
                        log.warn("Id is required");
                        yield new Repply<>(RepplyStatus.ERROR, "Id is required", null);
                    } else {
                        ProductDto dto = service.findById(cmd.id());
                        log.info("Reading product by id");
                        yield (dto == null) ? new Repply<>(RepplyStatus.ERROR, "Product not found", null)
                                : new Repply<>(RepplyStatus.SUCCESS, "Read product name", dto);
                    }
                }
                case CommandType.READ_ALL -> {
                    log.info("Reading all products");
                    yield new Repply<>(RepplyStatus.SUCCESS, "Read all products", service.findAll());
                }
                case CommandType.UPDATE -> {
                    if (cmd.body() == null || cmd.id() == null) {
                        log.warn("Id and body is required");
                        yield new Repply<>(RepplyStatus.ERROR, "Id and body is required", null);
                    } else {
                        ProductDto dto = service.update(cmd.id(), cmd.body());

                        if (dto != null) {
                            log.info("Updating product name={}, price={}", dto.name(), dto.price());
                            yield new Repply<>(RepplyStatus.SUCCESS, "Update product name", dto);
                        } else {
                            log.info("Product not found, null dto");
                            yield new Repply<>(RepplyStatus.ERROR, "Product not found", null);
                        }
                    }
                }
                case CommandType.DELETE -> {
                    if(cmd.id() == null) {
                        log.warn("Id is required");
                        yield new Repply<>(RepplyStatus.ERROR, "Id is required", null);
                    } else {
                        boolean result = service.delete(cmd.id());
                        log.info("Deleting product");
                        yield (result) ? new Repply<>(RepplyStatus.SUCCESS, "Deleting Product", "deleted")
                                : new Repply<>(RepplyStatus.ERROR, "Product not found", null);
                    }
                }
                default -> {
                    log.warn("Unknown command type={}", cmd.type());
                    yield new Repply<>(RepplyStatus.ERROR, "Unknown command type", null);
                }
            };

            return MessageBuilder.withPayload(reply)
                    .setHeader("correlationId", correlationId)
                    .build();
        };
    }
}