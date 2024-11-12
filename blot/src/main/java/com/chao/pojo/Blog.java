package com.chao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@TableName("m_blog")
@Accessors(chain = true)
public class Blog implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull
    private Integer userId;
    @NotEmpty
    private String title;
    private String content;
    private String description;
    private LocalDateTime created;
    @NotNull
    private Integer status;
    private Category category;
    private Integer good;
}
