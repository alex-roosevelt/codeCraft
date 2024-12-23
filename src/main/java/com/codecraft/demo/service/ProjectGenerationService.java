package com.codecraft.demo.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.ByteArrayResource;

public interface ProjectGenerationService {
  ByteArrayResource generateProject(Map<String, Object> params) throws IOException;
}
