package com.company.movie.service;

import org.springframework.web.client.RestClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class InegiService {
    private final RestClient restClient;

    public InegiService(RestClient.Builder builder) {
        // Configuramos la URL base y el timeout de 30 segundos
        this.restClient = builder
                .baseUrl("https://api.inegi.org.mx") // Reemplaza con tu URL base
                .requestFactory(new org.springframework.http.client.SimpleClientHttpRequestFactory() {{
                    setConnectTimeout(Duration.ofSeconds(30));
                    setReadTimeout(Duration.ofSeconds(30));
                }})
                .build();
    }
}
