package com.maiia.techtest.lorempost.posts.presentation;

import com.maiia.techtest.lorempost.posts.model.dto.PostDto;
import com.maiia.techtest.lorempost.posts.service.PostService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@Log
public class PostApi {

  private final PostService postService;

  @Autowired
  PostApi(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("")
  @CrossOrigin("http://localhost:3000")
  public ResponseEntity<Page<PostDto>> getPosts(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "50") int size,
      @Nullable Sort sort) {
    log.info("Getting posts page");
    return ResponseEntity.ok(this.postService.getPosts(PageRequest.of(page, size, sort)));
  }
}
