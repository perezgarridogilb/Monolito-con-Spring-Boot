package com.springcloud.springcloud.models;

public record Command<T>(String type, Long id, T body) {

}
