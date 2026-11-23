package ru.crm.report_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.crm.common_lib.dto.ProductDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public void processProductStream(Consumer<ProductDto> consumer) {
        restClient.get()
                .uri("/api/v2/products/stream")
                .exchange((request, response) -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (!line.isBlank()) {
                                ProductDto productDto = objectMapper.readValue(line, ProductDto.class);
                                consumer.accept(productDto);
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка чтения потока", e);
                    }
                    return response;
                });
    }

}
