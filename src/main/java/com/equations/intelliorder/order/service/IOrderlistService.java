package com.equations.intelliorder.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.requestVo.CustomerOrderReqVo;
import com.equations.intelliorder.order.requestVo.WaiterOrderReqVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-14
 */
public interface IOrderlistService extends IService<Orderlist> {

    List<Orderlist> showOrderlistList();    //返回待做菜品列表

    int receiveOrderlist(int listId, int staffId);  //厨师更改做菜状态

    int completeOrderlist(int listId, int staffId); //厨师完成做菜

    List<Orderlist> serveList();    //返回服务员上菜列表

    int receiveServe(int listId, int staffId);  //服务员接单上菜中

    int completeServe(int listId, int staffId); //服务员上菜完成

    List<Orderlist> showOrderInfo(int orderId);    //前台查看订单详情

    int waiterOrder(WaiterOrderReqVo waiterOrderReqVo); //服务员点餐系列操作

    List<Orderlist> customerOrder(CustomerOrderReqVo customerOrderReqVo, String openId); //顾客点餐系列操作

}
