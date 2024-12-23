package com.codecraft.demo.controller;

import com.codecraft.demo.model.ProjectRequest;
import com.codecraft.demo.service.ProjectGenerationService;
import com.codecraft.demo.service.ProjectGeneratorService;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectGeneratorService projectGeneratorService;
  private final ProjectGenerationService projectGenerationService;

  @PostMapping("/generate")
  public ResponseEntity<String> generateProject(@RequestBody ProjectRequest request) {
    try {
      Map<String, Object> params = Map.of(
          "packageName", request.getPackageName(),
          "appName", request.getAppName(),
          "artifactId", request.getArtifactId()
      );
      projectGeneratorService.generateProject("output/" + request.getAppName(), params);
      return ResponseEntity.ok("Project generated successfully!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error: " + e.getMessage());
    }
  }

  @PostMapping("/generate-zip")
  public ResponseEntity<ByteArrayResource> generateProject(@RequestBody Map<String, Object> params) throws IOException {
    // Генерация проекта
    ByteArrayResource file = projectGenerationService.generateProject(params);

    // Возврат файла как ответа
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=project.zip")
        .header(HttpHeaders.CONTENT_TYPE, "application/zip")
        .body(file);
  }
}
