package com.equations.intelliorder.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.dish.entity.Dish;
import com.equations.intelliorder.dish.mapper.DishMapper;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.mapper.OrderMapper;
import com.equations.intelliorder.order.mapper.OrderlistMapper;
import com.equations.intelliorder.order.requestVo.CustomerOrderReqVo;
import com.equations.intelliorder.order.requestVo.DishOrder;
import com.equations.intelliorder.order.requestVo.WaiterOrderReqVo;
import com.equations.intelliorder.order.service.IOrderlistService;
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
 * @since 2021-07-14
 */
@Service
public class OrderlistServiceImpl extends ServiceImpl<OrderlistMapper, Orderlist> implements IOrderlistService {

    @Autowired
    private OrderlistMapper orderlistMapper;//通过字段注入自动创建mapper映射类
    @Autowired
    private OrderMapper orderMapper;//调用order的mapper映射类
    @Autowired
    private DishMapper dishMapper;

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
    public int receiveOrderlist(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(1);
        orderlist.setStaffId(staffId);
        return orderlistMapper.update(orderlist, wrapper);
    }

    @Override   //厨师完成做菜
    public int completeOrderlist(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(2);
        return orderlistMapper.update(orderlist, wrapper);
    }

    @Override    //返回服务员上菜列表
    public List<Orderlist> serveList() {
        QueryWrapper<Orderlist> wrapper = new QueryWrapper<>();
        wrapper.between("listStatus", 2, 3);
        return orderlistMapper.selectList(wrapper);
    }

    @Override   //服务员接单上菜中
    public int receiveServe(int listId, int staffId) {
        UpdateWrapper<Orderlist> wrapper = new UpdateWrapper<>();
        wrapper.eq("listId", listId);
        Orderlist orderlist = orderlistMapper.selectOne(wrapper);
        orderlist.setListStatus(3);
        orderlist.setStaffId(staffId);
        return orderlistMapper.update(orderlist, wrapper);
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

//    @Override   //服务员添加菜品
//    public int addOrderlist(int dishId, int orderId) {
//        Orderlist orderlist = new Orderlist();
//        QueryWrapper<Order> wrapper = new QueryWrapper<>();
//        wrapper.eq("orderId", orderId);
//        orderlist.setDeskId(orderMapper.selectOne(wrapper).getDeskId());
//        orderlist.setDishId(dishId);
//        orderlist.setOrderId(orderId);
//        orderlist.setOrderTime(LocalDateTime.now());
//        orderlist.setDishNum(1);
//        orderlist.setDishPrice(dishService.getDishPriceById(dishId));
//        orderlist.setListStatus(0);
//        return orderlistMapper.insert(orderlist);
//    }

    @Override
    public int waiterOrder(WaiterOrderReqVo waiterOrderReqVo) {


        //服务员设置桌号，同时创建新order
        Order newOrder = new Order();
        newOrder.setDeskId(waiterOrderReqVo.getDeskId());
        newOrder.setOrderStatus(false);
        orderMapper.insert(newOrder);


        //通过桌号返回未付款订单号
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("orderStatus", false)
                .eq("deskId", waiterOrderReqVo.getDeskId());

        int orderId = orderMapper.selectOne(orderQueryWrapper).getOrderId();


        //        服务员添加菜品
        double totalPrice = 0;

        //        先遍历dishOrders数组
        for (DishOrder dishOrders : waiterOrderReqVo.getDishOrders())
            if (dishOrders.getDishNum() != 0) {
                //得到各种值填入orderlist中
                Orderlist orderlist = new Orderlist();
                orderlist.setDeskId(waiterOrderReqVo.getDeskId());
                orderlist.setDishId(dishOrders.getDishId());
                orderlist.setOrderId(orderId);
                orderlist.setOrderTime(LocalDateTime.now());
                orderlist.setDishNum(dishOrders.getDishNum());
                //查询dish表得到单价
                QueryWrapper<Dish> dishQueryWrapper = new QueryWrapper<>();
                dishQueryWrapper.eq("dishId", dishOrders.getDishId());
                double orderDishPrice =
                        dishMapper.selectOne(dishQueryWrapper).getDishPrice();
                orderlist.setDishPrice(orderDishPrice);
                totalPrice += dishOrders.getDishNum() * orderDishPrice;
                orderlist.setListStatus(0);
                orderlistMapper.insert(orderlist);
            }


        //服务员下单该订单
        UpdateWrapper<Order> orderWrapper = new UpdateWrapper<>();
        orderWrapper.eq("orderId", orderId);
        Order order = orderMapper.selectOne(orderWrapper);
        order.setOrderTime(LocalDateTime.now());
        order.setTotalPrice(totalPrice);
        return orderMapper.update(order, orderWrapper);
    }

    @Override
    public List<Orderlist> customerOrder(
            CustomerOrderReqVo customerOrderReqVo, String openId) {


        Order newOrder = new Order();
        newOrder.setDeskId(customerOrderReqVo.getDeskId());
        newOrder.setOrderStatus(false);
        newOrder.setOpenId(openId);
        orderMapper.insert(newOrder);


        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("orderStatus", false)
                .eq("deskId", customerOrderReqVo.getDeskId());

        int orderId = orderMapper.selectOne(orderQueryWrapper).getOrderId();


        //        服务员添加菜品
        double totalPrice = 0;

        //        先遍历dishOrders数组
        for (DishOrder dishOrders : customerOrderReqVo.getDishOrders()) {

            //得到各种值填入orderlist中
            Orderlist orderlist = new Orderlist();
            orderlist.setDeskId(customerOrderReqVo.getDeskId());
            orderlist.setDishId(dishOrders.getDishId());
            orderlist.setOrderId(orderId);
            orderlist.setOrderTime(LocalDateTime.now());
            orderlist.setDishNum(dishOrders.getDishNum());
            //查询dish表得到单价
            QueryWrapper<Dish> dishQueryWrapper = new QueryWrapper<>();
            dishQueryWrapper.eq("dishId", dishOrders.getDishId());
            double orderDishPrice =
                    dishMapper.selectOne(dishQueryWrapper).getDishPrice();
            orderlist.setDishPrice(orderDishPrice);
            totalPrice += dishOrders.getDishNum() * orderDishPrice;
            orderlist.setListStatus(0);
            orderlist.setOpenId(openId);
            orderlistMapper.insert(orderlist);

        }


        //服务员下单该订单
        UpdateWrapper<Order> orderWrapper = new UpdateWrapper<>();
        orderWrapper.eq("orderId", orderId);
        Order order = orderMapper.selectOne(orderWrapper);
        order.setOrderTime(LocalDateTime.now());
        order.setTotalPrice(totalPrice);
        orderMapper.update(order, orderWrapper);

        QueryWrapper<Orderlist> orderlistQueryWrapper = new QueryWrapper<>();
        orderlistQueryWrapper.eq("orderId", orderId);

        return orderlistMapper.selectList(orderlistQueryWrapper);

    }


}
