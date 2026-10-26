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
private final String baseUrl; 
    public InegiService(@Value("${api.inegi.uri}") String baseUrl) {
        this.baseUrl = baseUrl; // 2. La guardas en el campo de la clase
        // Configuramos la URL base y el timeout de 30 segundos
        this.restClient = RestClient.builder()
                .baseUrl(this.baseUrl)
                .requestFactory(new org.springframework.http.client.SimpleClientHttpRequestFactory() {{
                    setConnectTimeout(Duration.ofSeconds(30)); // timeout(30)
                    setReadTimeout(Duration.ofSeconds(30));
                }})
                .build();
    }

    public List<Map<String, Object>> getStates() {
        try {
            String endpoint = this.baseUrl + "/mgee/";

            Map<String, Object> response = this.restClient.get()
                    .uri(endpoint)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            return (List<Map<String, Object>>) response.get("datos");
     
        } catch (Exception e) {
            throw new RuntimeException("No se pudo consultar el servicio de INEGI.", e);
        }
    }

public List<Map<String, Object>> getMunicipalities(String keyState) {        try {
String endpoint = this.baseUrl + "/mgem/" + keyState;

            Map<String, Object> response = this.restClient.get()
                    .uri(endpoint)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            return (List<Map<String, Object>>) response.get("datos");
        } catch (Exception e) {
            throw new RuntimeException("No se pudo consultar el servicio de INEGI.", e);
        }
    }
}
