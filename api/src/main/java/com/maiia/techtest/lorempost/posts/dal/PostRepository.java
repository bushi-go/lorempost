package com.maiia.techtest.lorempost.posts.dal;

import com.maiia.techtest.lorempost.posts.model.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, Long> {}
