package ru.crm.report_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.crm.common_lib.dto.ProductDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public void processProductStream(Consumer<ProductDto> consumer) {
        log.info(">>> Отправка запроса в product-service");
        restClient.post()
                .uri("/api/v2/products/stream")
                .attributes(clientRegistrationId("product-service"))
                .exchange((request, response) -> {
                    log.info("Получен ответ со статусом: " + response.getStatusCode());

                    if (!response.getStatusCode().is2xxSuccessful()) {
                        log.error("product-service вернул ошибку: {}", response.getStatusCode());
                        return response;
                    }

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            log.info(">>> Прочитана строка: " + line);
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
        log.info(">>> Чтение потока завершено");
    }

}
