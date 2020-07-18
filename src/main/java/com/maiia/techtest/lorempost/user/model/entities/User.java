package com.maiia.techtest.lorempost.user.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.maiia.techtest.lorempost.core.model.dto.Address;
import com.maiia.techtest.lorempost.core.model.dto.Company;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// TODO : add a better management than @Data : id should not have a setter
@Builder(builderClassName = "UserBuilder")
@JsonDeserialize(builder = User.UserBuilder.class)
@Data
@Document("user")
public class User {
  @Id() @JsonIgnore private String id;

  @JsonProperty("id")
  @NotNull
  private Long originalId;

  @NotBlank private String name;
  @NotBlank private String username;
  @NotBlank @Email private String email;
  @NotNull private Address address;
  private String phone;
  private String website;
  @NotNull private Company company;

  @JsonPOJOBuilder(withPrefix = "")
  public static class UserBuilder {}
}
