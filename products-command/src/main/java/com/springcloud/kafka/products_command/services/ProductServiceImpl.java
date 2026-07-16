package com.springcloud.kafka.products_command.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcloud.kafka.products_command.entities.Product;
import com.springcloud.kafka.products_command.models.dto.ProductDto;
import com.springcloud.kafka.products_command.models.mapper.Mappers;
import com.springcloud.kafka.products_command.repositories.ProductRepository;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public ProductDto create(ProductDto dto) {
/*         Product product = new Product(dto.name(), dto.price());
        Product productNew = productRepository.save(product);

       return new ProductDto(
        productNew.getId(),
        productNew.getName(),
        productNew.getPrice()
       ); */
       return Mappers.toDto(productRepository.save(Mappers.toEntity(dto)));
    }

}
