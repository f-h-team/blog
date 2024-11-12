package com.chao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.dto.Result;
import com.chao.pojo.Comments;

public interface CommentsService extends IService<Comments> {
    Result add(Long commentId,Integer blogId,String content,String parentId);
    Result show(Integer blogId);
    Result showNoJudge();
    Result judge(String id,boolean pd);
}
