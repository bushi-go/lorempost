package com.maiia.techtest.lorempost.posts.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// TODO : add a better management than @Data : id and _id should not have a setter
@Builder(builderClassName = "PostBuilder")
@JsonDeserialize(builder = Post.PostBuilder.class)
@Data
@Document("posts")
public class Post {
  @Id() @JsonIgnore private String id;

  @JsonProperty("id")
  @NotNull
  private Long originalId;

  @NotNull private Long userId;
  @NotBlank private String title;
  @NotBlank private String body;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PostBuilder {}
}
