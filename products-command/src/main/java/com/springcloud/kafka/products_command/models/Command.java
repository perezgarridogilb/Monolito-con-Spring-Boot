package com.springcloud.kafka.products_command.models;

public record Command<T>(String type, Long id, T body) {

}
