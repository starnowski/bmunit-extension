package com.com.github.starnowski.bmunit.extension.junit5.spring.demo.model;

import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

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
@ToString(of = {"id", "name"})
@Table(name = "app_user")
public class User {
    @Id
    private long id;
    private String password;
    private String email;
    private String emailVerificationHash;
}
