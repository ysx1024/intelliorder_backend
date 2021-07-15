package com.equations.intelliorder.order.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.service.IOrderService;
import com.equations.intelliorder.order.service.IOrderlistService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */
@RestController
@RequestMapping("/order/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;//通过字段注入自动创建业务类，调用orderlistService类

    @RequestMapping(value = "/showOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回查看未付款的订单列表", notes = "前台查看列表返回")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showOrderList() {
        Map<String, Object> map = new HashMap<>();
        try {

            List<Order> orderList = orderService.showOrderList();
            map.put("status", "200");
            map.put("data", orderList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

}
