package com.chao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chao.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    Blog selectOne(Integer id);
    List<Blog> selectAll();
    List<Blog> selectByUserId(Integer userId);
    Integer save(Blog blog);
}
