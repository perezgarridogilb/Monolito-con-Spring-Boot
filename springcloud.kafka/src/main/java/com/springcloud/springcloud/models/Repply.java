package com.springcloud.springcloud.models;
// Uso de Genéricos (<T>): El uso de <T> permite que tu clase Repply sea flexible. Esto significa que el mismo objeto puede transportar diferentes tipos de datos
public record Repply<T>(String status, String message, T body) {

}
