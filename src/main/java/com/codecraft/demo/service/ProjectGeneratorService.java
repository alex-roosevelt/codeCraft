package com.codecraft.demo.service;

import java.io.IOException;
import java.util.Map;

public interface ProjectGeneratorService {
  void generateProject(String outputDir, Map<String, Object> params) throws IOException;
}
