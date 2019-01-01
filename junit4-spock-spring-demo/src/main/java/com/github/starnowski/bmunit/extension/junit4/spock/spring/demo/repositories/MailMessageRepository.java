package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.repositories;

import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.model.MailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailMessageRepository extends JpaRepository<MailMessage, Long> {
}
