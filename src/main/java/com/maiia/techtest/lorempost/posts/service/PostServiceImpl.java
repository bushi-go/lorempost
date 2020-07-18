package com.maiia.techtest.lorempost.posts.service;

import com.maiia.techtest.lorempost.core.exception.model.ApiException;
import com.maiia.techtest.lorempost.core.exception.model.ErrorCode;
import com.maiia.techtest.lorempost.posts.dal.PostRepository;
import com.maiia.techtest.lorempost.posts.model.dto.PostDto;
import com.maiia.techtest.lorempost.posts.model.entities.Post;
import com.maiia.techtest.lorempost.posts.model.mappers.PostMapper;
import com.maiia.techtest.lorempost.user.dal.UserRepository;
import com.maiia.techtest.lorempost.user.model.entities.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

  private final PostRepository postRepo;
  private final UserRepository userRepo;
  private final PostMapper postMapper;
  private static List<String> sortableFields = List.of("title", "author");

  @Autowired
  PostServiceImpl(
      PostRepository postRepository, UserRepository userRepository, PostMapper postMapper) {
    this.postRepo = postRepository;
    this.userRepo = userRepository;
    this.postMapper = postMapper;
  }

  @Override
  public Page<PostDto> getPosts(Pageable criteria) {
    log.debug("Checking criteria");

    Pageable actualCriteria = criteria;
    if (!criteria.getSort().isSorted()) {
      log.info("Applying default sorting : by title,ascending");
      actualCriteria =
          PageRequest.of(
              criteria.getPageNumber(),
              criteria.getPageSize(),
              Sort.by(Sort.Direction.ASC, "title"));
    }

    if (criteria.getSort().isSorted()
        && criteria
            .getSort()
            .get()
            .anyMatch(order -> !sortableFields.contains(order.getProperty()))) {
      throw new ApiException(ErrorCode.INVALID_SORT_FIELD);
    }
    if (criteria.getSort().get().anyMatch(order -> "author".equals(order.getProperty()))) {
      actualCriteria =
          PageRequest.of(
              criteria.getPageNumber(),
              criteria.getPageSize(),
              criteria
                  .getSort()
                  .get()
                  .map(
                      order -> {
                        if ("author".equals(order.getProperty())) {
                          return Sort.by(order.getDirection(), "userId");
                        }
                        return Sort.by(order);
                      })
                  .reduce(Sort.unsorted(), Sort::and));
    }
    log.info("Retrieving posts");
    Page<Post> page = this.postRepo.findAll(actualCriteria);
    log.info("Retrieving posts'authors");
    List<User> authors =
        this.userRepo.findAllByOriginalIdIn(
            page.getContent().stream()
                .map(Post::getUserId)
                .distinct()
                .collect(Collectors.toList()));
    log.info("Mapping to postDto");
    Map<Post, Optional<User>> postAndAuthorMap =
        page.getContent().stream()
            .collect(
                Collectors.toMap(
                    post -> post,
                    post ->
                        authors.stream()
                            .filter(author -> author.getOriginalId().equals(post.getUserId()))
                            .findFirst()));
    Page<PostDto> result =
        page.map(
            post -> {
              if (postAndAuthorMap.get(post).isPresent()) {
                return postMapper.toDtoWithAuthor(post, postAndAuthorMap.get(post).get());
              } else {
                log.warn("Found post without author : " + post);
                return postMapper.toDto(post);
              }
            });
    return result;
  }
}
