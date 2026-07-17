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
private final ProductService productService;

    public ProductCommandConsumer(ProductService productService) {
    this.productService = productService;
}
@Bean
public Function<Message<Command<ProductDto>>, Message<Repply<?>>> handleCommands() {
return message -> {
        Command<ProductDto> cmd = message.getPayload(); // Obtienes el payload del mensaje
        String type = cmd.type() == null ? "" : cmd.type().toUpperCase();
        
         Repply<?> result = switch (type) {
            case "CREATE" -> {
                if (cmd.body() == null) {
                    yield new Repply<>("ERROR", "Create EMPTY BODY", null);
                }
                ProductDto productSave = productService.create(cmd.body());
                yield new Repply<>("SUCCESS", "Product created", productSave);
            }
            case "UPDATE" -> {
                // Implementar lógica y retornar un Repply
                yield new Repply<>("SUCCESS", "Update not implemented yet", null);
            }
            // ... resto de casos
            default -> {
                log.warn("Unknown command type ={}", type);
                yield new Repply<>("ERROR", "Unknown command", null);
            }
        };
        
        return MessageBuilder.<Repply<?>>withPayload(result).build();
    };
}
}
