package com.equations.intelliorder.dish.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.equations.intelliorder.dish.entity.Dish;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-13
 */
public interface IDishService extends IService<Dish> {

    List<Dish> showDishList();//渲染时返回所有菜品的信息列表

    List<Dish> showDish();//返回所有上架菜品列表

    Dish getDishId(int dishId);//通过菜品id查询

    List<Dish> getDishName(String dishName);//根据菜品名称查询

    List<Dish> getDishType(String dishType);//根据菜品类型查询菜品

    int updateDishState(int dishId);//通过id修改上下架状态

    int updateDish(int dishId, String dishName, String dishType, double dishPrice,
                   String dishImage, String dishDesc, double costPrice);//修改菜品具体信息

    int addDish(String dishName, String dishType, double dishPrice,
                String dishImage, String dishDesc, double costPrice);//增加菜品信息

    int deleteByDishId(int dishId);//通过序号删除菜品

    String getDishImage(int dishId);//通过序号查找图片

    String getDishName(int dishId);//通过序号查找名称

}
