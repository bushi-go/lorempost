package com.maiia.techtest.lorempost.core.config;

import java.net.URI;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@ConfigurationProperties(prefix = "com.maiia.techtest.lorempost")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class ExternalSource {
  @NotBlank private URI postUrl;
  @NotBlank private URI userUrl;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
