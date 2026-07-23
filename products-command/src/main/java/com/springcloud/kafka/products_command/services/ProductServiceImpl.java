package com.springcloud.kafka.products_command.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springcloud.kafka.products_command.entities.Product;
import com.springcloud.kafka.products_command.models.dto.ProductDto;
import com.springcloud.kafka.products_command.models.mapper.Mappers;
import com.springcloud.kafka.products_command.repositories.ProductRepository;

import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public ProductDto findById(Long id) {
return productRepository.findById(id)
    .map(Mappers::toDto)
    .orElse(null);
    }

    @Override
    @Transactional/* (readOnly = true) */
    public List<ProductDto> findAll() {
        return ((List<Product>)productRepository.findAll())
        .stream() 
        .map(Mappers::toDto)
        .toList();
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
Optional<Product> entityOptional = productRepository.findById(id);
if (entityOptional.isPresent()) {
    Product entity = entityOptional.orElse(null);
    entity.setName(dto.name());
    entity.setPrice(dto.price());
    return Mappers.toDto(productRepository.save(entity));
}
return null;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        boolean result = productRepository.existsById(id);
        if (result) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    

}
