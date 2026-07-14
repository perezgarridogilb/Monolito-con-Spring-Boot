package com.springcloud.springcloud.services;

import com.springcloud.springcloud.models.dto.ProductDto;

public interface ProductCommandService {

    void sendCreate(ProductDto dto);
}
