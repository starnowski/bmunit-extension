package com.com.github.starnowski.bmunit.extension.junit5.spring.demo.repositories;

import com.github.starnowski.bmunit.extension.junit5.spring.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
