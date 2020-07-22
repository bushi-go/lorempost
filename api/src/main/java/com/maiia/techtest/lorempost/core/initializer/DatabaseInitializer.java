package com.maiia.techtest.lorempost.core.initializer;

import com.maiia.techtest.lorempost.core.config.ExternalSource;
import com.maiia.techtest.lorempost.posts.dal.PostRepository;
import com.maiia.techtest.lorempost.posts.model.entities.Post;
import com.maiia.techtest.lorempost.user.dal.UserRepository;
import com.maiia.techtest.lorempost.user.model.entities.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@EnableConfigurationProperties(ExternalSource.class)
public class DatabaseInitializer {

  private final PostRepository postRepo;
  private final UserRepository userRepo;
  private final RestTemplate restTemplate;
  private final ExternalSource externalSource;

  @Autowired
  DatabaseInitializer(
      PostRepository postRepo,
      UserRepository userRepo,
      RestTemplate restTemplate,
      ExternalSource externalSource) {
    this.userRepo = userRepo;
    this.postRepo = postRepo;
    this.restTemplate = restTemplate;
    this.externalSource = externalSource;
  }

  @EventListener
  public void initializePosts(ContextRefreshedEvent event) {

    ResponseEntity<Post[]> response =
        restTemplate.getForEntity(externalSource.getPostUrl(), Post[].class);
    List<Post> result = Arrays.asList(response.getBody());
    this.postRepo.saveAll(result);
  }

  @EventListener
  public void initializeUser(ContextRefreshedEvent event) {
    ResponseEntity<User[]> response =
        restTemplate.getForEntity(externalSource.getUserUrl(), User[].class);
    List<User> result = Arrays.asList(response.getBody());
    this.userRepo.saveAll(result);
  }
}
