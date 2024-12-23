package com.codecraft.demo.service.impl;

import com.codecraft.demo.service.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

  private final Configuration freemarkerConfig;

  @Override
  public String generateFromTemplate(String templateName, Map<String, Object> dataModel) {
    try {
      Template template = freemarkerConfig.getTemplate(templateName);
      StringWriter writer = new StringWriter();
      template.process(dataModel, writer);
      return writer.toString();
    } catch (Exception e) {
      throw new RuntimeException("Error while processing template", e);
    }
  }
}
