package com.chao.service.impl;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.dto.Result;
import com.chao.mapper.CommentsMapper;
import com.chao.pojo.Comments;
import com.chao.service.CommentsService;
import com.chao.utils.ThreadLocalUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {
    @Override
   public Result add(Long commentId,Integer blogId,String content,String parentId){
        //todo 获得用户id
       Map<String,Object> o = (Map<String,Object>)ThreadLocalUtils.get();
       Integer userId = (Integer)o.get("userId");
       //设置状态为待审核：0
       Comments comments = new Comments().setId(UUID.randomUUID().toString(true))
               .setBlogId(blogId)
               .setContent(content).setCreateTime(LocalDateTime.now())
               .setUserId(userId).setParentId(parentId).setStatus(0);

       save(comments);
        return Result.success();
    }
   //获得某博客的评论列表
    public Result show(Integer blogId){
        List<Comments> root = getBaseMapper().getRoot(blogId);
        return Result.success(root);
    }
    //展示未审核的评论
    @Override
    public Result showNoJudge() {
        List<Comments> commentsList= list(new QueryWrapper<Comments>()
                .select("id", "user_id","content", "create_time","blog_id")
                .eq("status", 0));
        return Result.success(commentsList);
    }
    //审核单个评论
    @Override
    public Result judge(String id,boolean pd) {
        boolean  result = false;
        if(pd){
            result=update(new UpdateWrapper<Comments>().set("status", 1).eq("id", id));
        }else{
            result = remove(new QueryWrapper<Comments>().eq("id", id));
        }
        return result?Result.success():Result.error("失败");
    }
    
}
