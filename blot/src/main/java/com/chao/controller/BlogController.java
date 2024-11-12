package com.chao.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chao.dto.Result;
import com.chao.pojo.Blog;
import com.chao.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;
    /*
    * 增加博客
    * */
    @PostMapping("/add")
    public Result addBlog(@RequestBody Blog blog){
        blogService.save(blog);
        return Result.success();
    }
    /*
    * 展示db中所有博客
    * */
    @GetMapping("/showAll")
    public Result show(){

        return blogService.selectAll();
    }
    /*
    * 根据用户id展示博客
    * */
    @GetMapping("/showByUser")
    public Result showByUser(Integer userId){
        List<Blog> blogList = blogService.list(new QueryWrapper<Blog>().eq("user_id", userId));
        return Result.success(blogList);
    }
    /*
    * 修改博客
    * */
    @GetMapping("/update")
    public Result updateBlog(@RequestBody Blog blog){

        return Result.success();
    }
    /*
    * 删除个人博客
    * */
    @DeleteMapping("/del/{userId}")
    public Result delBlog(@PathVariable Integer userId){
        return Result.success();
    }
}
