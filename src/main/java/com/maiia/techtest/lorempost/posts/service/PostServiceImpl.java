package com.maiia.techtest.lorempost.posts.service;

import com.maiia.techtest.lorempost.posts.dal.PostRepository;
import com.maiia.techtest.lorempost.posts.model.dto.PostDto;
import com.maiia.techtest.lorempost.posts.model.entities.Post;
import com.maiia.techtest.lorempost.posts.model.mappers.PostMapper;
import com.maiia.techtest.lorempost.user.dal.UserRepository;
import com.maiia.techtest.lorempost.user.model.entities.User;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log
public class PostServiceImpl implements PostService {

  private final PostRepository postRepo;
  private final UserRepository userRepo;
  private final PostMapper postMapper;

  @Autowired
  PostServiceImpl(
      PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
    this.postRepo = postRepository;
    this.userRepo = userRepository;
    this.postMapper = postMapper;
  }

  @Override
  public Page<PostDto> getPosts(Pageable criteria) {
    log.info("Retrieving posts");
    Page<Post> page = this.postRepo.findAll(criteria);
    log.info("Retrieving posts'authors");
    List<User> authors =
        this.userRepo.findAllByOriginalIdIn(
            page.getContent().stream()
                .map(Post::getUserId)
                .distinct()
                .collect(Collectors.toList()));
    log.info("Mapping to postDto");
    Map<Post, User> postAndAuthorMap =
        page.getContent().stream()
            .collect(
                Collectors.toMap(
                    post -> post,
                    post ->
                        authors.stream()
                            .filter(author -> author.getOriginalId().equals(post.getUserId()))
                            .findFirst()
                            .orElseThrow(
                                () ->
                                    new InvalidParameterException(
                                        "Found a post without author " + post.getId()))));
    Page<PostDto> result =
        page.map(post -> postMapper.toDtoWithAuthor(post, postAndAuthorMap.get(post)));
    return result;
  }
}
