package com.springcloud.kafka.products_command.handlers;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springcloud.kafka.products_command.models.Command;
import com.springcloud.kafka.products_command.models.dto.ProductDto;

@Configuration
public class ProductCommandConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductCommandConsumer.class);

    @Bean
public Consumer<Command<ProductDto>> handleCommands() {
    return cmd -> {
        log.info("Comando recibido y consumido con éxito: type={}, body={}", cmd.type(), cmd.body());
    };
}
}
