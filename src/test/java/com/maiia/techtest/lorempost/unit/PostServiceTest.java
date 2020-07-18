package com.maiia.techtest.lorempost.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.github.javafaker.Faker;
import com.maiia.techtest.lorempost.core.exception.model.ApiException;
import com.maiia.techtest.lorempost.core.exception.model.ErrorCode;
import com.maiia.techtest.lorempost.core.initializer.DatabaseInitializer;
import com.maiia.techtest.lorempost.core.model.dto.Address;
import com.maiia.techtest.lorempost.core.model.dto.Company;
import com.maiia.techtest.lorempost.core.model.dto.Coordinates;
import com.maiia.techtest.lorempost.posts.dal.PostRepository;
import com.maiia.techtest.lorempost.posts.model.dto.PostDto;
import com.maiia.techtest.lorempost.posts.model.entities.Post;
import com.maiia.techtest.lorempost.posts.model.mappers.PostMapper;
import com.maiia.techtest.lorempost.posts.service.PostService;
import com.maiia.techtest.lorempost.user.dal.UserRepository;
import com.maiia.techtest.lorempost.user.model.entities.User;
import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PostServiceTest {

  private static int DEFAULT_PAGE_NUMBER = 0;
  private static int DEFAULT_PAGE_SIZE = 50;
  private static Sort DEFAULT_PAGE_SORT = Sort.by(Sort.Direction.ASC, "title");
  private static Pageable DEFAULT_PAGE_CRITERIA =
      PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SORT);

  @Autowired private PostService postService;
  @Autowired private PostMapper postMapper;
  @MockBean private PostRepository postRepo;

  @MockBean private UserRepository userRepo;
  @MockBean private DatabaseInitializer initializer;

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  public class GetPaginatedPostTest {

    private List<Post> postList;
    private List<User> userList;
    private Faker faker = Faker.instance();

    @BeforeAll
    public void setup() {
      userList =
          IntStream.range(1, 11)
              .mapToObj(
                  index -> PostServiceTest.getRandomUser(Integer.valueOf(index).longValue(), faker))
              .collect(Collectors.toList());
      postList =
          userList.stream()
              .flatMap(
                  usr ->
                      IntStream.range(1, 11)
                          .mapToObj(
                              index ->
                                  PostServiceTest.getRandomPost(
                                      index + (usr.getOriginalId() - 1) * 10, usr, faker)))
              .collect(Collectors.toList());
    }

    @Test
    public void givenASetOfPostWithAutorPresentShouldReturnThem() {
      when(postRepo.findAll(eq(DEFAULT_PAGE_CRITERIA)))
          .thenReturn(
              new PageImpl<Post>(
                  postList.stream()
                      .sorted(Comparator.comparing(Post::getTitle))
                      .limit(DEFAULT_PAGE_SIZE)
                      .collect(Collectors.toList()),
                  DEFAULT_PAGE_CRITERIA,
                  postList.size()));
      when(userRepo.findAllByOriginalIdIn(any()))
          .thenReturn(
              userList.stream()
                  .filter(
                      usr ->
                          postList.stream()
                              .sorted(Comparator.comparing(Post::getTitle))
                              .limit(DEFAULT_PAGE_SIZE)
                              .map(Post::getUserId)
                              .collect(Collectors.toList())
                              .contains(usr.getOriginalId()))
                  .collect(Collectors.toList()));
      Page<PostDto> expectedResult =
          new PageImpl<PostDto>(
              postList.stream()
                  .sorted(Comparator.comparing(Post::getTitle))
                  .limit(DEFAULT_PAGE_SIZE)
                  .map(
                      post ->
                          postMapper.toDtoWithAuthor(
                              post,
                              userList.stream()
                                  .filter(usr -> usr.getOriginalId().equals(post.getUserId()))
                                  .findFirst()
                                  .orElseThrow(() -> new InvalidParameterException())))
                  .collect(Collectors.toList()),
              DEFAULT_PAGE_CRITERIA,
              postList.size());
      Page<PostDto> actualResult = postService.getPosts(DEFAULT_PAGE_CRITERIA);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenLimitShouldLimit() {
      Pageable criteria = PageRequest.of(DEFAULT_PAGE_NUMBER, 10, DEFAULT_PAGE_SORT);
      when(postRepo.findAll(eq(criteria)))
          .thenReturn(
              new PageImpl<Post>(
                  postList.stream()
                      .sorted(Comparator.comparing(Post::getTitle))
                      .limit(criteria.getPageSize())
                      .collect(Collectors.toList()),
                  criteria,
                  postList.size()));
      when(userRepo.findAllByOriginalIdIn(any()))
          .thenReturn(
              userList.stream()
                  .filter(
                      usr ->
                          postList.stream()
                              .sorted(Comparator.comparing(Post::getTitle))
                              .limit(criteria.getPageSize())
                              .map(Post::getUserId)
                              .collect(Collectors.toList())
                              .contains(usr.getOriginalId()))
                  .collect(Collectors.toList()));
      Page<PostDto> expectedResult =
          new PageImpl<PostDto>(
              postList.stream()
                  .sorted(Comparator.comparing(Post::getTitle))
                  .limit(10)
                  .map(
                      post ->
                          postMapper.toDtoWithAuthor(
                              post,
                              userList.stream()
                                  .filter(usr -> usr.getOriginalId().equals(post.getUserId()))
                                  .findFirst()
                                  .orElseThrow(() -> new InvalidParameterException())))
                  .collect(Collectors.toList()),
              criteria,
              postList.size());
      Page<PostDto> actualResult = postService.getPosts(criteria);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenPageNumberShouldReturnWithOffset() {
      Pageable criteria = PageRequest.of(4, 10, DEFAULT_PAGE_SORT);
      when(postRepo.findAll(eq(criteria)))
          .thenReturn(
              new PageImpl<Post>(
                  postList.stream()
                      .sorted(Comparator.comparing(Post::getTitle))
                      .skip(5 * 10)
                      .limit(criteria.getPageSize())
                      .collect(Collectors.toList()),
                  criteria,
                  postList.size()));
      when(userRepo.findAllByOriginalIdIn(any()))
          .thenReturn(
              userList.stream()
                  .filter(
                      usr ->
                          postList.stream()
                              .sorted(Comparator.comparing(Post::getTitle))
                              .skip(5 * 10)
                              .limit(criteria.getPageSize())
                              .map(Post::getUserId)
                              .collect(Collectors.toList())
                              .contains(usr.getOriginalId()))
                  .collect(Collectors.toList()));
      Page<PostDto> expectedResult =
          new PageImpl<PostDto>(
              postList.stream()
                  .sorted(Comparator.comparing(Post::getTitle))
                  .skip(5 * 10)
                  .limit(criteria.getPageSize())
                  .map(
                      post ->
                          postMapper.toDtoWithAuthor(
                              post,
                              userList.stream()
                                  .filter(usr -> usr.getOriginalId().equals(post.getUserId()))
                                  .findFirst()
                                  .orElseThrow(() -> new InvalidParameterException())))
                  .collect(Collectors.toList()),
              criteria,
              postList.size());
      Page<PostDto> actualResult = postService.getPosts(criteria);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenSortByAuthorShouldReturnSortedByUserId() {
      Pageable criteria =
          PageRequest.of(
              DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "author"));
      Pageable actualCriteria =
          PageRequest.of(
              DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "userId"));

      when(postRepo.findAll(eq(actualCriteria)))
          .thenReturn(
              new PageImpl<Post>(
                  postList.stream()
                      .sorted(Comparator.comparing(Post::getUserId))
                      .limit(criteria.getPageSize())
                      .collect(Collectors.toList()),
                  criteria,
                  postList.size()));
      when(userRepo.findAllByOriginalIdIn(any()))
          .thenReturn(
              userList.stream()
                  .filter(
                      usr ->
                          postList.stream()
                              .sorted(Comparator.comparing(Post::getUserId))
                              .limit(criteria.getPageSize())
                              .map(Post::getUserId)
                              .collect(Collectors.toList())
                              .contains(usr.getOriginalId()))
                  .collect(Collectors.toList()));
      Page<PostDto> expectedResult =
          new PageImpl<PostDto>(
              postList.stream()
                  .sorted(Comparator.comparing(Post::getUserId))
                  .limit(criteria.getPageSize())
                  .map(
                      post ->
                          postMapper.toDtoWithAuthor(
                              post,
                              userList.stream()
                                  .filter(usr -> usr.getOriginalId().equals(post.getUserId()))
                                  .findFirst()
                                  .orElseThrow(() -> new InvalidParameterException())))
                  .collect(Collectors.toList()),
              criteria,
              postList.size());
      Page<PostDto> actualResult = postService.getPosts(criteria);
      assertEquals(expectedResult, actualResult);
    }

    @Test
    public void givenUnsortablePropertyShouldThrow() {
      Pageable criteria =
          PageRequest.of(
              DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.by(Sort.Direction.ASC, "toto"));
      ApiException thrown = assertThrows(ApiException.class, () -> postService.getPosts(criteria));
      assertEquals(ErrorCode.INVALID_SORT_FIELD, thrown.getCode());
    }
  }

  private static User getRandomUser(long index, Faker faker) {

    return User.builder()
        .originalId(index)
        .name(faker.name().fullName())
        .username(faker.name().username())
        .email(faker.internet().emailAddress())
        .address(
            Address.builder()
                .street(faker.address().streetName())
                .city(faker.address().city())
                .suite(faker.address().streetAddressNumber())
                .zipcode(faker.address().zipCode())
                .geo(
                    Coordinates.builder()
                        .lng(Double.parseDouble(faker.address().longitude()))
                        .lat(Double.parseDouble(faker.address().latitude()))
                        .build())
                .build())
        .company(
            Company.builder()
                .name(faker.company().name())
                .catchPhrase(faker.company().catchPhrase())
                .bs(faker.company().bs())
                .build())
        .build();
  }

  private static Post getRandomPost(long index, User usr, Faker faker) {
    return Post.builder()
        .originalId(index)
        .body(faker.lorem().paragraph(faker.number().numberBetween(4, 10)))
        .title(faker.lorem().sentence(faker.number().numberBetween(1, 3)))
        .userId(usr.getOriginalId())
        .build();
  }
}
