package com.maiia.techtest.lorempost.posts.service;

import com.maiia.techtest.lorempost.posts.model.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

  Page<PostDto> getPosts(Pageable criteria);
}
