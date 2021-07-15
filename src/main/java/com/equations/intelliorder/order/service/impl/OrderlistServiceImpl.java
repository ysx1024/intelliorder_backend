package com.equations.intelliorder.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.mapper.OrderlistMapper;
import com.equations.intelliorder.order.service.IOrderlistService;
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
 * @since 2021-07-14
 */
@Service
public class OrderlistServiceImpl extends ServiceImpl<OrderlistMapper, Orderlist> implements IOrderlistService {

    @Autowired
    private OrderlistMapper orderlistMapper;//通过字段注入自动创建mapper映射类

    @Override
    public List<Orderlist> showOrderlistList() {
        //1)创建QueryWrapper对象
         QueryWrapper<Orderlist> wrapper = new QueryWrapper<Orderlist>();
        //通过status<=1的条件返回所有待做菜品信息
        wrapper.lt("listStatus",2);
        //2)执行查询
        return orderlistMapper.selectList(wrapper);
    }
}
