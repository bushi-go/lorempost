package com.maiia.techtest.lorempost.user.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorDto {
  @NotNull private Long id;
  @NotBlank private String username;
}
