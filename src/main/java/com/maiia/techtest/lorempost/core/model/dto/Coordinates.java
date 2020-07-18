package com.maiia.techtest.lorempost.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder(builderClassName = "CoordinatesBuilder")
@JsonDeserialize(builder = Coordinates.CoordinatesBuilder.class)
@Value
public class Coordinates {
  private Double lng;
  private Double lat;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CoordinatesBuilder {}
}
