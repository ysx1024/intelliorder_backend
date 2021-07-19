package com.equations.intelliorder.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.equations.intelliorder.dish.service.IDishService;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.mapper.OrderMapper;
import com.equations.intelliorder.order.mapper.OrderlistMapper;
import com.equations.intelliorder.order.service.IOrderlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.equations.intelliorder.user.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Autowired
    private IDishService dishService;
    @Autowired
    private OrderMapper orderMapper;

    @Override   //返回待做菜品列表
    public List<Orderlist> showOrderlistList() {
        //1)创建QueryWrapper对象
        QueryWrapper<Orderlist> wrapper = new QueryWrapper<>();
        //通过status<=1的条件返回所有待做菜品信息
        wrapper.lt("listStatus", 2);
        //2)执行查询
        return orderlistMapper.selectList(wrapper);
    }

    @Override   //厨师更改做菜状态
    public  int receiveOrderlist(int listId,int staffId){
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId",listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(1);
        orderlist.setStaffId(staffId);
        return orderlistMapper.update(orderlist,wrapper);
    }

    @Override   //厨师完成做菜
    public int completeOrderlist(int listId,int staffId){
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId",listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(2);
        return orderlistMapper.update(orderlist,wrapper);
    }

    @Override    //返回服务员上菜列表
    public List<Orderlist> serveList(){
        QueryWrapper<Orderlist> wrapper = new QueryWrapper<>();
        wrapper.between("listStatus",2,3);
        return orderlistMapper.selectList(wrapper);
    }

    @Override   //服务员接单上菜中
    public int receiveServe(int listId,int staffId){
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId",listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(3);
        orderlist.setStaffId(staffId);
        return orderlistMapper.update(orderlist,wrapper);
    }

    @Override   //服务员上菜完成
    public int completeServe(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(4);
        return orderlistMapper.update(orderlist, wrapper);
    }

    @Override   //前台查看订单详情
    public List<Orderlist> showOrderInfo(int orderId) {
        //1)创建QueryWrapper对象
        QueryWrapper<Orderlist> wrapper = new QueryWrapper<>();
        wrapper.eq("orderId", orderId);
        //2)执行查询
        return orderlistMapper.selectList(wrapper);
    }

    @Override   //服务员添加菜品
    public int addOrderlist(int dishId, int orderId) {
        Orderlist orderlist = new Orderlist();
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("orderId", orderId);
        orderlist.setDeskId(orderMapper.selectOne(wrapper).getDeskId());
        orderlist.setDishId(dishId);
        orderlist.setOrderId(orderId);
        orderlist.setOrderTime(LocalDateTime.now());
        orderlist.setDishNum(1);
        orderlist.setDishPrice(dishService.getDishPriceById(dishId));
        orderlist.setListStatus(0);
        return orderlistMapper.insert(orderlist);
    }

}
