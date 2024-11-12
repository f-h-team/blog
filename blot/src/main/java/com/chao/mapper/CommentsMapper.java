package com.chao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chao.pojo.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

    List<Comments> getRoot(Integer blogId);
    Comments selectChild(String id);
}
