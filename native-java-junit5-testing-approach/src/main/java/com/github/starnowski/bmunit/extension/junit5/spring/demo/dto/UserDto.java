package com.github.starnowski.bmunit.extension.junit5.spring.demo.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name"})
public class UserDto {
    private long id;
    private String password;
    private String email;
}
