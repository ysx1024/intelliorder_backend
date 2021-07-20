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

//       int setDesk(int deskId);    //服务员设置桌号，同时创建新order

    int updateOrderState(int orderId);        //通过订单Id为当前订单结账

//       int getOrderByDeskId(int deskId);         //通过桌号返回未付款订单号


}
