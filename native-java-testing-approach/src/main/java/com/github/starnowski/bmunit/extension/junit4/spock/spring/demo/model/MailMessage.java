package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Entity
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "mailTo", "mailSubject"})
@Table(name = "mail_message")
public class MailMessage {
    @Id
    private long id;
    private String mailFrom;
    private String mailTo;
    private String mailSubject;
    @Column(columnDefinition = "TEXT")
    private String mailContent;
    private String contentType;
}
