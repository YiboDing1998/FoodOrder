package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {


  @Autowired
  private ShoppingCartMapper shoppingCartMapper;

  @Autowired
  private DishMapper dishMapper;

  @Autowired
  private SetmealMapper setmealMapper;

  @Override
  public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart shoppingCart = new ShoppingCart();
    BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
    Long userId = BaseContext.getCurrentId();
    List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

    if (list != null && list.size() > 0) {
      ShoppingCart cart = list.get(0);
      cart.setNumber(cart.getNumber() + 1);
      shoppingCartMapper.update(cart);
    } else {
      Long dishId = shoppingCart.getDishId();
      if (dishId != null) {
        Dish dish = dishMapper.getById(dishId);
        shoppingCart.setName(dish.getName());
        shoppingCart.setImage(dish.getImage());
        shoppingCart.setAmount(dish.getPrice());
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
      } else {
      Long mealId = shoppingCartDTO.getSetmealId();
      Setmeal setmeal = (Setmeal) setmealMapper.getDishItemBySetmealId(mealId);
        shoppingCart.setName(setmeal.getName());
        shoppingCart.setImage(setmeal.getImage());
        shoppingCart.setAmount(setmeal.getPrice());
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
      }
    }
    shoppingCartMapper.insert(shoppingCart);
  }

  @Override
  public List<ShoppingCart> showShoppingCart() {
    Long userId = BaseContext.getCurrentId();
    ShoppingCart shoppingCart = ShoppingCart.builder().userId(userId).build();
    List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
    return list;
  }

  @Override
  public void delete() {
    Long userId = BaseContext.getCurrentId();
    shoppingCartMapper.deleteByUserId(userId);
  }


}
