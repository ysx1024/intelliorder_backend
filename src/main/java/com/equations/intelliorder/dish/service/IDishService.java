package com.equations.intelliorder.dish.service;

import com.equations.intelliorder.dish.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
