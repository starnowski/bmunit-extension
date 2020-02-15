package com.github.starnowski.bmunit.extension.junit5.spring.demo.repositories;

import com.github.starnowski.bmunit.extension.junit5.spring.demo.model.MailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailMessageRepository extends JpaRepository<MailMessage, Long> {
}
