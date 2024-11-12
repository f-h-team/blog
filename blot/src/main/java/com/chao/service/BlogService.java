package com.chao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.dto.Result;
import com.chao.pojo.Blog;

import java.util.List;

public interface BlogService extends IService<Blog> {
    Result selectOne(Integer id);
    Result selectAll();
    Result selectByUserId(Integer userId);
    Result mySave(Blog blog);
}
