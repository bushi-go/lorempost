package com.maiia.techtest.lorempost.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Builder(builderClassName = "AddressBuilder")
@JsonDeserialize(builder = Address.AddressBuilder.class)
@Value
public class Address {
  @NotBlank private String street;
  @NotBlank private String suite;
  @NotBlank private String city;
  @NotBlank private String zipcode;
  @NotNull private Coordinates geo;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AddressBuilder {}
}
