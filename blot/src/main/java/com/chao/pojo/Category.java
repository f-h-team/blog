package com.chao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
@Data
@TableName("m_category")
public class Category implements Serializable {
    private int id;
    @NotEmpty
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
