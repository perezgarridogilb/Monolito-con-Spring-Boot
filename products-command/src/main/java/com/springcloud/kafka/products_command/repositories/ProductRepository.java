package com.springcloud.kafka.products_command.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springcloud.kafka.products_command.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
//@NativeQuery(value = "SELECT * FROM user", resultClass = User.class)
}// Mapeo de Resultados de Consultas
