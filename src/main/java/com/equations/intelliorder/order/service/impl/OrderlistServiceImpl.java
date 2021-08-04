package com.equations.intelliorder.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.mapper.OrderMapper;
import com.equations.intelliorder.order.mapper.OrderlistMapper;
import com.equations.intelliorder.order.requestVo.DishOrder;
import com.equations.intelliorder.order.requestVo.OrderReqVo;
import com.equations.intelliorder.order.service.IOrderlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-14
 */
@Service
public class OrderlistServiceImpl extends ServiceImpl<OrderlistMapper, Orderlist> implements IOrderlistService {

    @Autowired
    private OrderlistMapper orderlistMapper;//通过字段注入自动创建mapper映射类

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Orderlist> showOrderlistList() {
        //1)创建QueryWrapper对象
        QueryWrapper<Orderlist> wrapper = new QueryWrapper<>();
        //通过status<=1的条件返回所有待做菜品信息
        wrapper.lt("listStatus", 2);
        //2)执行查询
        return orderlistMapper.selectList(wrapper);
    }

    @Override
    public int receiveOrderlist(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(1);
        orderlist.setStaffId(staffId);
        return orderlistMapper.update(orderlist, wrapper);
    }

    @Override
    public int completeOrderlist(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(2);
        return orderlistMapper.update(orderlist, wrapper);
    }

    @Override
    public List<Orderlist> serveList() {
        QueryWrapper<Orderlist> wrapper = new QueryWrapper<>();
        wrapper.between("listStatus", 2, 3);
        return orderlistMapper.selectList(wrapper);
    }

    @Override
    public int receiveServe(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(3);
        orderlist.setStaffId(staffId);
        return orderlistMapper.update(orderlist, wrapper);
    }

    @Override
    public int completeServe(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(4);
        return orderlistMapper.update(orderlist, wrapper);
    }

    @Override
    public List<Orderlist> showOrderInfo(int orderId) {
        QueryWrapper<Orderlist> wrapper = new QueryWrapper<>();
        wrapper.eq("orderId", orderId);
        return orderlistMapper.selectList(wrapper);

    }

    @Override
    public int waiterOrder(OrderReqVo orderReqVo) {

        //通过桌号查询为点菜还是加菜
        AtomicInteger orderId = new AtomicInteger();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("orderStatus", false)
                .eq("deskId", orderReqVo.getDeskId());

        //如果此订单号存在（即可以查到即值大于0）则直接赋值并进行加菜
        //        如果不存在则创建订单进行点餐
        Order existOrder = orderMapper.selectOne(orderQueryWrapper);
        if (existOrder != null && existOrder.getOrderId() > 0) {
            orderId.set(existOrder.getOrderId());
        } else {
            //如果不存在则创建订单进行点餐
            //服务员设置桌号，同时创建新order
            Order newOrder = new Order();
            newOrder.setDeskId(orderReqVo.getDeskId());
            newOrder.setOrderStatus(false);
            newOrder.setTotalPrice(0.0);
            orderMapper.insert(newOrder);
            //通过桌号返回新建订单号
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("orderStatus", false)
                    .eq("deskId", orderReqVo.getDeskId());
            orderId.set(orderMapper.selectOne(wrapper).getOrderId());

            //        服务员添加菜品
            //        先遍历dishOrders数组
            for (DishOrder dishOrder : orderReqVo.getDishOrders())
                //确认此点菜信息是否有效
                if (dishOrder.getDishNum() != 0) {
                    //得到各种值填入orderlist中
                    Orderlist orderlist = new Orderlist();
                    orderlist.setDeskId(orderReqVo.getDeskId());
                    orderlist.setDishId(dishOrder.getDishId());
                    orderlist.setOrderId(orderId.intValue());
                    orderlist.setOrderTime(LocalDateTime.now());
                    orderlist.setDishNum(dishOrder.getDishNum());
                    orderlist.setDishPrice(dishOrder.getDishPrice());
                    orderlist.setListStatus(0);
                    orderlistMapper.insert(orderlist);
                }

        }


        //服务员下单该订单
        UpdateWrapper<Order> orderWrapper = new UpdateWrapper<>();
        orderWrapper.eq("orderId", orderId.intValue());
        Order order = orderMapper.selectOne(orderWrapper);
        order.setOrderTime(LocalDateTime.now());
        order.setTotalPrice(orderReqVo.getTotalPrice() + order.getTotalPrice());
        return orderMapper.update(order, orderWrapper);
    }

    @Override
    public List<Orderlist> customerOrder(
            OrderReqVo orderReqVo, String openId) {

        //通过桌号查询为点菜还是加菜
        AtomicInteger orderId = new AtomicInteger();
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("orderStatus", false)
                .eq("deskId", orderReqVo.getDeskId());
        //如果此订单号存在（即可以查到即值大于0）则直接赋值并进行加菜
        //如果不存在则创建订单进行点餐
        Order existOrder = orderMapper.selectOne(orderQueryWrapper);
        if (existOrder != null && existOrder.getOrderId() > 0)
            orderId.set(orderMapper.selectOne(orderQueryWrapper).getOrderId());
        else {
            //设置桌号，同时创建新order
            Order newOrder = new Order();
            newOrder.setDeskId(orderReqVo.getDeskId());
            newOrder.setOrderStatus(false);
            newOrder.setTotalPrice(0.0);
            orderMapper.insert(newOrder);
            //通过桌号返回新建订单号
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("orderStatus", false)
                    .eq("deskId", orderReqVo.getDeskId());
            orderId.set(orderMapper.selectOne(wrapper).getOrderId());
        }


        //        添加菜品
        //        先遍历dishOrders数组
        for (DishOrder dishOrder : orderReqVo.getDishOrders())
            //确认此点菜信息是否有效
            if (dishOrder.getDishNum() != 0) {
                //得到各种值填入orderlist中
                Orderlist orderlist = new Orderlist();
                orderlist.setDeskId(orderReqVo.getDeskId());
                orderlist.setDishId(dishOrder.getDishId());
                orderlist.setOrderId(orderId.intValue());
                orderlist.setOrderTime(LocalDateTime.now());
                orderlist.setDishNum(dishOrder.getDishNum());
                orderlist.setDishPrice(dishOrder.getDishPrice());
                orderlist.setListStatus(0);
                orderlist.setOpenId(openId);
                orderlistMapper.insert(orderlist);
            }


        //顾客下单该订单
        UpdateWrapper<Order> orderWrapper = new UpdateWrapper<>();
        orderWrapper.eq("orderId", orderId.intValue());
        Order order = orderMapper.selectOne(orderWrapper);
        order.setOrderTime(LocalDateTime.now());
        order.setTotalPrice(orderReqVo.getTotalPrice());

        orderMapper.update(order, orderWrapper);
        //返回orderlist下所有的订单
        QueryWrapper<Orderlist> orderlistQueryWrapper = new QueryWrapper<>();
        orderlistQueryWrapper.eq("orderId", orderId.intValue());
        return orderlistMapper.selectList(orderlistQueryWrapper);

    }
}


