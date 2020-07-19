package com.maiia.techtest.lorempost.user.dal;

import com.maiia.techtest.lorempost.user.model.entities.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
  List<User> findAllByOriginalIdIn(List<Long> originalIds);
}
