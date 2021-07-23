package com.equations.intelliorder.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.equations.intelliorder.order.entity.Order;

import com.equations.intelliorder.order.mapper.OrderMapper;
import com.equations.intelliorder.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> showOrderList() {
        //1)创建QueryWrapper对象
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        //通过status==0的条件返回未付款订单信息
        wrapper.eq("orderStatus", 0);
        return orderMapper.selectList(wrapper);

    }



    @Override   //通过订单Id进行付款逻辑
    public int updateOrderState(int orderId) {
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("orderId", orderId);
        Order order = orderMapper.selectOne(wrapper);
        order.setOrderStatus(true);
        LocalDateTime dataTime = LocalDateTime.now();
        order.setEndTime(dataTime);//设置结账时间
        return orderMapper.update(order, wrapper);
    }

    @Override
    public Order toPay(int orderId) {
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.eq("orderId",orderId);
        return orderMapper.selectOne(wrapper);
    }

}
