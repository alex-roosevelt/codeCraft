package com.codecraft.demo.service.impl;

import com.codecraft.demo.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGPTServiceImpl implements ChatGPTService {

  @Value("${gpt.secret.key}")
  private String openAIKey;

  @Value("${gpt.url}")
  private String openAIUrl;

  public String generateCode(String prompt) {
    // Заголовки
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + openAIKey);
    headers.set("Content-Type", "application/json");

    // Тело запроса
    String requestBody = """
        {
            "model": "gpt-3.5-turbo",
            "prompt": "%s",
            "max_tokens": 1500,
            "temperature": 0.7
        }
        """.formatted(prompt);

    // Отправка запроса
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> response = restTemplate.postForEntity(openAIUrl, entity, String.class);

    // Обработка ответа
    return response.getBody();
  }
}
