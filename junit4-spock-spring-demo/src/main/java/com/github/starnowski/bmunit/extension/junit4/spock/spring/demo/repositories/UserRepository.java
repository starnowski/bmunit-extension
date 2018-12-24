package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.repositories;

import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.model.User;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, Long>{
}
