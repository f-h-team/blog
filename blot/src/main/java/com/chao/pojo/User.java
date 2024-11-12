package com.chao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@TableName("m_user")
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;
    @NotEmpty
    private String userName;
    private String password;
    private String avatar;
    private String email;
    private Integer status;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private Integer roleId;
}
