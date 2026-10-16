package com.company.movie.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class InegiService {
    private final RestClient restClient;

    public InegiService(RestClient.Builder builder, @Value("${api.inegi.uri}") String baseUrl) {
        // Configuramos la URL base y el timeout de 30 segundos
        this.restClient = builder
                .baseUrl(baseUrl)
                .requestFactory(new org.springframework.http.client.SimpleClientHttpRequestFactory() {{
                    setConnectTimeout(Duration.ofSeconds(30)); // timeout(30)
                    setReadTimeout(Duration.ofSeconds(30));
                }})
                .build();
    }

    public List<Map<String, Object>> getStates() {
        try {
            return this.restClient.get()
                    .uri("/mgee/")
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("No se pudo consultar el servicio de INEGI.", e);
        }
    }

    public List<Map<String, Object>> getMunicipalities(String keyState) {
        try {
            return this.restClient.get()
                    .uri("/mgem/{keyState}", keyState)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            throw new RuntimeException("No se pudo consultar el servicio de INEGI.", e);
        }
    }
}
