package com.codecraft.demo.service.impl;

import com.codecraft.demo.service.ProjectGeneratorService;
import com.codecraft.demo.service.TemplateService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectGeneratorServiceImpl implements ProjectGeneratorService {

  private final TemplateService templateService;

  @Override
  public void generateProject(String outputDir, Map<String, Object> params) throws IOException {
    // Создаем структуру директорий
    Path projectPath = Paths.get(outputDir);
    Files.createDirectories(projectPath);

    // Генерируем основные файлы
    saveFile(projectPath.resolve("Dockerfile"),
        templateService.generateFromTemplate("project/Dockerfile.ftl", params));

    saveFile(projectPath.resolve(".gitlab-ci.yml"),
        templateService.generateFromTemplate("project/gitlab-ci.ftl", params));

    saveFile(projectPath.resolve("pom.xml"),
        templateService.generateFromTemplate("project/pom.ftl", params));

    // Пример генерации кода
    String packagePath = params.get("packageName").toString().replace(".", "/");
    Path mainPath = projectPath.resolve("src/main/java").resolve(packagePath);
    Files.createDirectories(mainPath);

    saveFile(mainPath.resolve("Application.java"),
        templateService.generateFromTemplate("project/Application.ftl", params));
  }

  private void saveFile(Path path, String content) throws IOException {
    Files.writeString(path, content);
  }
}
