package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingcart")
public class ShoppingCartController {

  @Autowired
  private ShoppingCartService shoppingCartService;

  @PostMapping("/add")
  public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
    shoppingCartService.addShoppingCart(shoppingCartDTO);
    return Result.success();
  }

  @GetMapping("/list")
  public Result<List<ShoppingCart>> list() {
   List<ShoppingCart> list = shoppingCartService.showShoppingCart();
   return Result.success(list);
  }

  @DeleteMapping("/delete")
  public Result delete() {
    shoppingCartService.delete();
    return Result.success();
  }


}
