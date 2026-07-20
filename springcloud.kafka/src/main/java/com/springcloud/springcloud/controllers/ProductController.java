package com.springcloud.springcloud.controllers;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springcloud.springcloud.models.Repply;
import com.springcloud.springcloud.models.dto.ProductDto;
import com.springcloud.springcloud.services.ProductCommandService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductCommandService commandService;

    public ProductController(ProductCommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductDto dto) {
        Repply<?> reply = commandService.sendCreateAndAwait(dto, Duration.ofSeconds(5));
 
        return extracted(reply);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Repply<?> reply = commandService.sendReadAndAwait(id, Duration.ofSeconds(5));

        return extracted(reply);
        // return getResponseEntity(reply);
    }

    private ResponseEntity<?> extracted(Repply<?> reply) {
        if ("SUCCESS".equalsIgnoreCase(reply.status())) {
            return ResponseEntity.ok(reply.body());
        }
        return ResponseEntity.badRequest().body(Map.of("error", reply.message()));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        Repply<?> repply = commandService.sendReadAllAndAwait(Duration.ofSeconds(5));
        return extracted(repply);
    }

}
