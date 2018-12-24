package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;

@Slf4j
@Entity
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "mailTo", "mailSubject"})
public class MailMessage {
    private long id;
    private String mailFrom;
    private String mailTo;
    private String mailSubject;
    @Column(columnDefinition = "TEXT")
    private String mailContent;
    private String contentType;
}
