package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {

  @Autowired
  private DishService dishService;

  @Autowired
 // private RedisTemplate redisTemplate;




  //根据分类id查询菜品
  @GetMapping("/list")
  @CachePut(cacheNames = "用户查询菜品", key = "#categoryId")
  //如果使用注解，key的生成 = 用户查询菜品：：categoryId
  public Result<List<DishVO>> list(Long categoryId) {
    //查询redis里是否存在菜品数据
    String key = "dish_" + categoryId;
    List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
    if (list != null && list.size() > 0) {
      return Result.success(list);
    } else {
      Dish dish = new Dish();
      dish.setCategoryId(categoryId);
      dish.setStatus(StatusConstant.ENABLE);
      List<DishVO> list2 = dishService.listWithFlavor(dish);
      redisTemplate.opsForValue().set(key, list2);
      return Result.success(list2);

    }

  }
}
