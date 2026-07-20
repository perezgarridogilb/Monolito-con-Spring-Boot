package com.springcloud.springcloud.services;

import java.time.Duration;

import com.springcloud.springcloud.models.Repply;
import com.springcloud.springcloud.models.dto.ProductDto;

public interface ProductCommandService {

    Repply<?> sendCreateAndAwait(ProductDto dto, Duration timeout );

    Repply<?> sendReadAndAwait(Long id, Duration timeout);

    Repply<?> sendReadAllAndAwait(Duration timeout);
    
}
