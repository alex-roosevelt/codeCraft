package com.codecraft.demo.config;

import freemarker.template.Configuration;
import java.io.IOException;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class FreeMarkerConfig {

  @Bean
  public Configuration freemarkerConfiguration() throws IOException {
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
    cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
    cfg.setDefaultEncoding("UTF-8");
    return cfg;
  }
}
