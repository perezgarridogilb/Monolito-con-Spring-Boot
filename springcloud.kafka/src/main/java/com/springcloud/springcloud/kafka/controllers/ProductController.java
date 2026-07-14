package com.springcloud.springcloud.kafka.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springcloud.springcloud.models.dto.ProductDto;
import com.springcloud.springcloud.services.ProductCommandService;

import jakarta.validation.Valid;

@RestController("/products")
public class ProductController {

    private final ProductCommandService commandService;

    public ProductController(ProductCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto dto) {
        commandService.sendCreate(dto);
        return ResponseEntity.ok().body(Map.of("Message", "Success sent"));
    }

}
