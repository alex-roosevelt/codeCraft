package com.codecraft.demo.service.impl;

import com.codecraft.demo.service.ProjectGenerationService;
import com.codecraft.demo.service.TemplateService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

@Service
public class ProjectGenerationServiceImpl implements ProjectGenerationService {

  @Autowired
  private TemplateService templateService;

  @Override
  // Генерирует проект и возвращает его как байтовый массив
  public ByteArrayResource generateProject(Map<String, Object> params) throws IOException {
    // Создаем поток для архивирования
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);

    // Генерация файлов с помощью шаблонов
    String pomContent = templateService.generateFromTemplate("project/pom.ftl", params);
    addFileToZip(zipOutputStream, "pom.xml", pomContent);

    String dockerfileContent = templateService.generateFromTemplate("project/Dockerfile.ftl", params);
    addFileToZip(zipOutputStream, "Dockerfile", dockerfileContent);

    String gitlabCiContent = templateService.generateFromTemplate("project/gitlab-ci.ftl", params);
    addFileToZip(zipOutputStream, ".gitlab-ci.yml", gitlabCiContent);

    String controllerContent = templateService.generateFromTemplate("project/Controller.ftl", params);
    addFileToZip(zipOutputStream, "src/main/java/"+ params.get("packageName") + "/controller/Controller.java", controllerContent);

    String appContent = templateService.generateFromTemplate("project/Controller.ftl", params);
    addFileToZip(zipOutputStream, "src/main/java/"+ params.get("packageName") + "/Application.java", appContent);

    // Закрываем поток архивации
    zipOutputStream.finish();
    zipOutputStream.close();

    // Возвращаем результат как ByteArrayResource для ответа
    return new ByteArrayResource(byteArrayOutputStream.toByteArray());
  }

  private void addFileToZip(ZipOutputStream zipOutputStream, String filePath, String fileContent) throws IOException {
    // Создаем запись в архиве
    ZipEntry zipEntry = new ZipEntry(filePath);  // полный путь к файлу
    zipOutputStream.putNextEntry(zipEntry);
    zipOutputStream.write(fileContent.getBytes());
    zipOutputStream.closeEntry();
  }
}
