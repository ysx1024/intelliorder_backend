package com.equations.intelliorder.dish.service;

import com.equations.intelliorder.dish.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import com.equations.intelliorder.user.entity.Staff;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-13
 */
public interface IDishService extends IService<Dish> {

    Dish getDishId(int dishId);//通过菜品id查询

    List<Dish> getDishName(String dishName);//根据菜品名称查询

    List<Dish> getDishType(String dishType);//根据菜品类型查询菜品

    int updateDishState(int dishId);//通过id修改上下架状态
}
