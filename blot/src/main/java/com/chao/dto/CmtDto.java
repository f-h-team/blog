package com.chao.dto;

import com.chao.pojo.Comments;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CmtDto {
    private Integer userId;
    private String content;
    private String parentId;
    private LocalDateTime createTime;
    private Integer blogId;
    private Integer status;
    private String id;
    private CmtDto childComments;
}
