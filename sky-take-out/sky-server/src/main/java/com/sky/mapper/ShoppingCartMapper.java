package com.sky.mapper;

import com.sky.entity.ShoppingCart;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

  List<ShoppingCart> list(ShoppingCart shoppingCart);


 @Update("update shopping_cart set number = #{number} where id = #{id}")
  void update(ShoppingCart shoppingCart);


 @Insert("inser into shopping cart (name, user_id, dish_id, setmeal_id, dish_flavor, number)" +
         "values (@{name}, #{dishId})")
 void insert(ShoppingCart shoppingCart);

 @Delete("delete from shopping_cart where user_id = #{userId}")
  void deleteByUserId(Long userId);
}
