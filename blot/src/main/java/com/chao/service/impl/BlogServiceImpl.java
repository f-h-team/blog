package com.chao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.dto.Result;
import com.chao.mapper.BlogMapper;
import com.chao.pojo.Blog;
import com.chao.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    @Override
    public Result selectOne(Integer id) {
        return Result.success(getBaseMapper().selectOne(id));
    }

    @Override
    public Result selectAll() {
        return Result.success(getBaseMapper().selectAll());
    }

    @Override
    public Result selectByUserId(Integer userId) {
        return Result.success(getBaseMapper().selectByUserId(userId));
    }

    @Override
    public Result mySave(Blog blog) {
        return Result.success(getBaseMapper().save(blog));
    }


}
