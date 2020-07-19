package com.maiia.techtest.lorempost.user.model.mappers;

import com.maiia.techtest.lorempost.user.model.dto.AuthorDto;
import com.maiia.techtest.lorempost.user.model.dto.UserDto;
import com.maiia.techtest.lorempost.user.model.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(source = "user.id", target = "originalId")
  User fromDto(UserDto user);

  @Mapping(source = "user.originalId", target = "id")
  UserDto toDto(User user);

  @Mapping(source = "user.originalId", target = "id")
  AuthorDto toAuthorDto(User user);
}
