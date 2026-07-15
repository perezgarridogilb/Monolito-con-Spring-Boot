package com.springcloud.kafka.products_command.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, length = 50)
private String name;

private Double price;

@Column(nullable = false, updatable = false)
private LocalDateTime createdAt;

@Column(nullable = false)
private LocalDateTime updatedAt;

@PrePersist
public void prePersist() {
    updatedAt = LocalDateTime.now();
     createdAt = LocalDateTime.now();
}

@PreUpdate
public void preUpdate()  {
    updatedAt = LocalDateTime.now();
}
// jpa
public Product() {
  
}
// instanciar
public Product(String name, Double price) {
    this.name = name;
    this.price = price;
}



}
