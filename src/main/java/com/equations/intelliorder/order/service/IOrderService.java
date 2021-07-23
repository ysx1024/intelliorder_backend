package com.equations.intelliorder.order.service;

import com.equations.intelliorder.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */
public interface IOrderService extends IService<Order> {

    List<Order> showOrderList();    //前台查看未付款的订单

    int updateOrderState(int orderId);        //通过订单Id为当前订单结账

    Order toPay(int orderId);    //结账


}
