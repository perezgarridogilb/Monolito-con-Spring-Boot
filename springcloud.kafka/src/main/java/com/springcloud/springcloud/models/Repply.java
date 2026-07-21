package com.springcloud.springcloud.models;
//Su único propósito es transportar datos entre diferentes capas de tu aplicación (por ejemplo, empaquetar
// Uso de Genéricos (<T>): El uso de <T> permite que tu clase Repply sea flexible. Esto significa que el mismo objeto puede transportar diferentes tipos de datos
public record Repply<T>(RepplyStatus status, String message, T body) {

}
