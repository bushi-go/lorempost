package com.maiia.techtest.lorempost.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Builder(builderClassName = "CompanyBuilder")
@JsonDeserialize(builder = Company.CompanyBuilder.class)
@Value
public class Company {
  @NotBlank private String name;
  @NotBlank private String catchPhrase;
  @NotBlank private String bs;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CompanyBuilder {}
}
