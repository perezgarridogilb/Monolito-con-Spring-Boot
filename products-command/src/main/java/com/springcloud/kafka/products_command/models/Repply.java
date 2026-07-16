package com.springcloud.kafka.products_command.models;

public record Repply<T>(String status, String message, T body) {

}
