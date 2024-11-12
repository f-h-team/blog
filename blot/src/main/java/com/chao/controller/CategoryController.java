package com.chao.controller;

import com.chao.dto.Result;
import com.chao.pojo.Category;
import com.chao.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory( @RequestBody Category category){
        category.setCreateTime(LocalDateTime.now());
        categoryService.save(category);
        return Result.success();
    }
    @GetMapping
    public Result getCategory(){
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }
    @PutMapping
    public Result updateCategory( @RequestBody Category category){
        boolean b = categoryService.updateById(category);

        return  Result.success();
    }
    @GetMapping("/detail")
    public Result getCategoryDetail(@RequestParam int id){
        Category categoryById = categoryService.getById(id);
        return Result.success(categoryById);
    }
    @DeleteMapping
    public Result deleteCategory(@RequestParam int id){
        categoryService.removeById(id);
        return Result.success();
    }
}
