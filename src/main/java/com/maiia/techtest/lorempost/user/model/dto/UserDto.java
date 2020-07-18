package com.maiia.techtest.lorempost.user.model.dto;

import com.maiia.techtest.lorempost.core.model.dto.Address;
import com.maiia.techtest.lorempost.core.model.dto.Company;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

  @NotNull private Long id;

  @NotBlank private String name;
  @NotBlank private String username;
  @NotBlank @Email private String email;
  @NotNull private Address address;
  private String phone;
  private String website;
  @NotNull private Company company;
}
