package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

  @Autowired
  private DishMapper dishMapper;

  @Autowired
  private DishFlavorMapper dishFlavorMapper;

  @Autowired
  private SetmealDishMapper setmealDishMapper;

  @Override
  @Transactional
  public void saveWithFlavor(DishDTO dishDTO) {
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish);
    dishMapper.insert(dish); //向菜品表插入1条数据

    Long dishId = dish.getId();//获取insert语句生成的主键值

    List<DishFlavor> flavors = dishDTO.getFlavors();
    if (flavors != null && flavors.size() > 0) {
      flavors.forEach(f -> {
        f.setDishId(dishId);
      });
      dishFlavorMapper.insert(flavors); //向口味表插入n条数据
    }
  }

  /**
   * 分页查询，使用dishVO对象储存dishDTO没有的属性
   * @param dishPageQueryDTO
   * @return
   */
  @Override
  public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
    PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
    Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
    return new PageResult(page.getTotal(), page.getResult());
  }

  @Transactional
  public void deleteBatch(List<Long> ids) {
    //判断当前菜品是否能够删除--是否在售卖中
    for (Long id : ids) {
      Dish dish = dishMapper.getById(id);
      if (dish.getStatus() == StatusConstant.ENABLE) {
        throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);

      }
    }
    //是否在套餐里
    List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
    if (setmealIds != null && setmealIds.size() > 0) {
      throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
    }

    //删除菜品
//    for (Long id : ids) {
//      dishMapper.deleteById(id);
//      //删除菜品关联口味数据
//      dishFlavorMapper.deleteByDishId(id);
//
//    }

    //优化
    //不用for loop了，批量删除
    dishMapper.deleteByIds(ids);
    dishFlavorMapper.deleteByDishIds(ids);


  }

  @Override
  public DishVO getByIdWithFlavor(Long id) {
      //先查询菜品表
    Dish dish =  dishMapper.getById(id);
      //查询口味表
    List<DishFlavor> listFlavor =  dishFlavorMapper.getByDishId(id);
      //封装一起到vo里
    DishVO dishVO = new DishVO();
    BeanUtils.copyProperties(dish, dishVO);
    dishVO.setFlavors(listFlavor);
      return null;
  }

  /**
   * 更新菜品
   * @param dishDTO
   */
  @Override
  public void updateWithFlavor(DishDTO dishDTO) {
    //修改菜品表
    Dish dish = new Dish();
    BeanUtils.copyProperties(dishDTO, dish);
    dishMapper.update(dish);

    //修改口味表
    dishFlavorMapper.deleteByDishId(dishDTO.getId());
    List<DishFlavor> flavors = dishDTO.getFlavors();
    if (flavors != null && flavors.size() > 0) {
      flavors.forEach(f -> {
        f.setDishId(dishDTO.getId());
      });
      dishFlavorMapper.insert(flavors); //向口味表插入n条数据
    }

  }

  @Override
  public List<DishVO> listWithFlavor(Dish dish) {
    List<Dish> dishList = dishMapper.list(dish);
    List<DishVO> dishVOList = new ArrayList<>();
    for (Dish d : dishList) {
      DishVO dishVO = new DishVO();
      BeanUtils.copyProperties(d, dishVO);
      List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());
      dishVO.setFlavors(flavors);
      dishVOList.add(dishVO);
    }
    return dishVOList;
  }
}
              