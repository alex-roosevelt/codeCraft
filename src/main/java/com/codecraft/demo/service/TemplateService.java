package com.codecraft.demo.service;

import java.util.Map;

public interface TemplateService {
  String generateFromTemplate(String templateName, Map<String, Object> dataModel);
}
