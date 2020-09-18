package com.innotechnum.practice.repos;

import com.innotechnum.practice.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
