package com.springcloud.kafka.products_command.services;

import java.util.List;

import com.springcloud.kafka.products_command.models.dto.ProductDto;

public interface ProductService {
    ProductDto create(ProductDto dto);
         ProductDto findById(Long id);
         List<ProductDto> findAll();
         ProductDto update(Long id, ProductDto dto);
        boolean delete(Long id);
    
}
