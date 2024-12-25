package com.codecraft.demo.service.impl;

import com.codecraft.demo.service.HuggingFaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class HuggingFaceServiceImpl implements HuggingFaceService {

  @Value("${hf.key}")
  private String huggingFaceKey;

  @Value("${hf.url}")
  private String huggingFaceUrl;

  private final ObjectMapper objectMapper;

  /**
   * Генерирует код на основе переданного промпта
   *
   * @param prompt текстовая подсказка
   * @return сгенерированный текст
   */
  @Override
  public String generateCode(String prompt) {
    RestTemplate restTemplate = new RestTemplate();
    try {
      Map<String, String> requestBody = Map.of("inputs", prompt);

      String jsonBody = objectMapper.writeValueAsString(requestBody);

      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + huggingFaceKey);
      headers.set("Content-Type", "application/json");

      ResponseEntity<String> response = restTemplate.exchange(
          huggingFaceUrl, HttpMethod.POST,
          new org.springframework.http.HttpEntity<>(jsonBody, headers), String.class);

      return parseGeneratedText(response.getBody());

    } catch (Exception e) {
      return "Ошибка при генерации текста: " + e.getMessage();
    }
  }

  /**
   * Извлекает сгенерированный текст из ответа Hugging Face API
   *
   * @param responseBody тело ответа в JSON
   * @return сгенерированный текст
   */
  private String parseGeneratedText(String responseBody) {
    try {
      // JSON содержит массив с одним объектом, у которого есть ключ "generated_text"
      var responseArray = objectMapper.readValue(responseBody, Map[].class);
      if (responseArray.length > 0 && responseArray[0].containsKey("generated_text")) {
        return responseArray[0].get("generated_text").toString();
      }
    } catch (Exception e) {
      return "Не удалось извлечь сгенерированный текст.";
    }
    return "Не удалось извлечь сгенерированный текст.";
  }
}

