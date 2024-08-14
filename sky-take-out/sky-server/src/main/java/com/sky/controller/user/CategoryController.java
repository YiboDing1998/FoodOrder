package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.ApiOperation;

@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/list")
  @ApiOperation("查询分类")
  public Result<List<Category>> list(Integer type) {
    List<Category> list = categoryService.list(type);
    return Result.success(list);
  }
}
