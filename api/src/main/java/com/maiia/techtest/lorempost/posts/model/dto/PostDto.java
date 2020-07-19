package com.maiia.techtest.lorempost.posts.model.dto;

import com.maiia.techtest.lorempost.user.model.dto.AuthorDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

// TODO : add a better management than @Data : id should not have a setter
@Builder
@Data
public class PostDto {

  @NotNull private AuthorDto author;

  @NotNull private Long id;

  @NotBlank private String title;
  @NotBlank private String body;
}
