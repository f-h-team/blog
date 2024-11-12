package com.chao.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@TableName("m_comments")
@Accessors(chain = true)
public class Comments implements Serializable {
    private Integer userId;
    private String content;
    private String parentId;
    private LocalDateTime createTime;
    private Integer blogId;
    private Integer status;
    private String id;
    private List<Comments> child;
}
