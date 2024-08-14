package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin/dish")
public class DishController {
  @Autowired
  private DishService dishService;
  @PostMapping
  @ApiOperation("add new food")
  public Result save(@RequestBody DishDTO dishDTO) {
    dishService.saveWithFlavor(dishDTO);

    return Result.success();
  }

  @GetMapping("/page")
  @ApiOperation("菜品分页查询")
  public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
    PageResult pageResult =  dishService.pageQuery(dishPageQueryDTO);
    return Result.success(pageResult);
  }

  @DeleteMapping
  @ApiOperation("删除菜品")
  public Result delete(@RequestParam List<Long> ids) {
    dishService.deleteBatch(ids);
    return Result.success();
  }

  @GetMapping("/{id}")
  @ApiOperation("查询单个菜品，并返回具体数据")
  public Result<DishVO> getById(@PathVariable Long id) {
    DishVO dishVO =  dishService.getByIdWithFlavor(id);
    return Result.success(dishVO);
  }

  @PutMapping
  public Result modify(@RequestBody DishDTO dishDTO) {
    dishService.updateWithFlavor(dishDTO);

    return Result.success();
  }
}
