package com.codecraft.demo.model;

import lombok.Data;

@Data
public class ProjectRequest {
  private String packageName;
  private String appName;
  private String artifactId;
}
