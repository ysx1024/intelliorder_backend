package com.equations.intelliorder.dish.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.dish.entity.Dish;
import com.equations.intelliorder.dish.mapper.DishMapper;
import com.equations.intelliorder.dish.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-13
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

    @Autowired
    private DishMapper dishMapper;//通过字段注入自动创建mapper映射类


    @Override
    public List<Dish> showDishList() {
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        //通过id>=1的条件巧用ge方法返回所有菜品信息列表
        wrapper.ge("dishId", 1);
        return dishMapper.selectList(wrapper);
    }

    @Override
    public List<Dish> showDish() {
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        wrapper.eq("dishState", true);
        return dishMapper.selectList(wrapper);
    }

    @Override   //通过菜品id进行查询
    public Dish getDishId(int dishId) {
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        wrapper.eq("dishId", dishId);
        return dishMapper.selectOne(wrapper);
    }

    @Override   //通过菜品名进行查询
    public List<Dish> getDishName(String dishName) {
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        //模糊查询
        wrapper.like("dishName", dishName);
        return dishMapper.selectList(wrapper);

    }

    @Override  //通过菜品类别查询
    public List<Dish> getDishType(String dishType) {
        QueryWrapper<Dish> wrapper = new QueryWrapper<>();
        //模糊查询
        wrapper.like("dishType", dishType);
        return dishMapper.selectList(wrapper);
    }

    @Override   //通过id修改上下架状态
    public int updateDishState(int dishId) {
        UpdateWrapper<Dish> wrapper = new UpdateWrapper<>();
        wrapper.eq("dishId", dishId);
        Dish dish = dishMapper.selectOne(wrapper);
        dish.setDishState(!dish.isDishState());
        return dishMapper.update(dish, wrapper);
    }

    @Override //通过id修改具体菜单信息
    public int updateDish(int dishId, String dishName, String dishType, double dishPrice,
                          String dishImage, String dishDesc, double costPrice) {
        UpdateWrapper<Dish> wrapper = new UpdateWrapper<>();
        wrapper.eq("dishId", dishId);
        Dish dish = dishMapper.selectOne(wrapper);
        dish.setDishName(dishName);
        dish.setDishType(dishType);
        dish.setDishPrice(dishPrice);
        dish.setDishImage(dishImage);
        dish.setDishDesc(dishDesc);
        dish.setCostPrice(costPrice);
        return dishMapper.update(dish, wrapper);
    }

    @Override   //增加菜品信息
    public int addDish(String dishName, String dishType, double dishPrice,
                       String dishImage, String dishDesc, double costPrice) {
        Dish dish = new Dish();
        dish.setDishName(dishName);
        dish.setDishType(dishType);
        dish.setDishPrice(dishPrice);
        dish.setDishImage(dishImage);
        dish.setDishDesc(dishDesc);
        dish.setCostPrice(costPrice);
        dish.setDishState(true);
        return dishMapper.insert(dish);
    }

    @Override   //通过序号删除菜品
    public int deleteByDishId(int dishId) {
        return dishMapper.deleteById(dishId);
    }

}
