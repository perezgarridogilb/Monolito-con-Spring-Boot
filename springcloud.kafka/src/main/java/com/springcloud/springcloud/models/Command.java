package com.springcloud.springcloud.models;

public record Command<T>(CommandType type, Long id, T body) {

}
