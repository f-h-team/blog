package com.chao.dto;

import com.chao.pojo.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserInfo {
    private Integer id;
    private String userName;
    private String avatar;
    private String email;
    private Integer status;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private Role role;
}
