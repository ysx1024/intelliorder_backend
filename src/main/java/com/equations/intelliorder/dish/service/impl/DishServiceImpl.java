package com.equations.intelliorder.dish.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equations.intelliorder.dish.entity.Dish;
import com.equations.intelliorder.dish.mapper.DishMapper;
import com.equations.intelliorder.dish.service.IDishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-13
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

    @Autowired
    private DishMapper dishMapper;//通过字段注入自动创建mapper映射类


    @Override   //通过菜品id进行查询
    public List<Dish> getDishId(int dishId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("dishId",dishId);
        return dishMapper.selectList(wrapper);
    }
}
