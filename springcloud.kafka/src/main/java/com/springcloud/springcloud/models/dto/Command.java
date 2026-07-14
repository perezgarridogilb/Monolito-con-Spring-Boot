package com.springcloud.springcloud.models.dto;

public record Command<T>(String type, Long id, T body) {

}
