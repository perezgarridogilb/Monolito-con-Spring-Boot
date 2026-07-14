package com.springcloud.springcloud.services;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.springcloud.springcloud.models.dto.Command;
import com.springcloud.springcloud.models.dto.ProductDto;

@Service
public class ProductCommandServiceIml implements ProductCommandService {

    // El StreamBridge funciona como nuestro Event Dispatcher o Bus en Laravel
    private final StreamBridge bridge;

    public ProductCommandServiceIml(StreamBridge bridge) {
        this.bridge = bridge;
    }

    @Override
    public void sendCreate(ProductDto dto) {
        // Creamos el comando (equivale a instanciar un Job o Event en Laravel)
        Command<ProductDto> cmd = new Command<>("CREATE", null, dto);
        
        // Enviamos al canal definido como "commands-out-0"
        // '0' es el estándar para el primer binding de salida (productor)
        boolean sent = this.bridge.send("commands-out-0", cmd);

        // Validación de éxito similar a checar si un Job fue despachado
        if (!sent) {
            throw new IllegalStateException("No se pudo enviar el comando a kafka");
        }
    }
}