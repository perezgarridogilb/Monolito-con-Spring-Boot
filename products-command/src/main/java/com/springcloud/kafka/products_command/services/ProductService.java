package com.springcloud.kafka.products_command.services;

import com.springcloud.kafka.products_command.models.dto.ProductDto;

public interface ProductService {
    ProductDto create(ProductDto dto);
         ProductDto findById(Long id);
         Object findAll();

    
}
