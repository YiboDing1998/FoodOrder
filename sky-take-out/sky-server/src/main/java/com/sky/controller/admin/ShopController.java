package com.sky.controller.admin;


import com.sky.result.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {

  @Autowired
  private RedisTemplate redisTemplate;

  @PutMapping("/{status}")
  @ApiOperation("营业状态")
  public Result setStatus(@PathVariable Integer status) {
    redisTemplate.opsForValue().set("SHOP_STATUS", status);

    return Result.success();
  }


  @GetMapping("/status")
  @ApiOperation("GET STATUS")
  public Result<Integer> getStatus() {
  Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
  return Result.success(status);
  }
}
