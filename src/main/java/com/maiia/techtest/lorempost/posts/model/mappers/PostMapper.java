package com.maiia.techtest.lorempost.posts.model.mappers;

import com.maiia.techtest.lorempost.posts.model.dto.PostDto;
import com.maiia.techtest.lorempost.posts.model.entities.Post;
import com.maiia.techtest.lorempost.user.model.entities.User;
import com.maiia.techtest.lorempost.user.model.mappers.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    uses = {UserMapper.class},
    componentModel = "spring")
public interface PostMapper {

  @Mapping(source = "dto.author.id", target = "userId")
  Post fromDto(PostDto dto);

  @Mapping(source = "post.originalId", target = "id")
  PostDto toDto(Post post);

  @Mapping(source = "post.originalId", target = "id")
  @Mapping(source = "user", target = "author")
  PostDto toDtoWithAuthor(Post post, User user);
}
