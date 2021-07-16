package com.equations.intelliorder.order.service;

import com.equations.intelliorder.order.entity.Orderlist;
import com.baomidou.mybatisplus.extension.service.IService;
import com.equations.intelliorder.user.entity.Staff;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-14
 */
public interface IOrderlistService extends IService<Orderlist> {

    List<Orderlist> showOrderlistList();//返回待做菜品列表

    int receiveOrderlist(int listId,int staffId);//厨师更改做菜状态

    int completeOrderlist(int listId,int staffId);//厨师完成做菜

    List<Orderlist> serveList();//返回服务员上菜列表

    int receiveServe(int listId,int staffId);//服务员接单上菜中

    int completeServe(int listId,int staffId);//服务员上菜完成

}
