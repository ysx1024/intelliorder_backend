package com.equations.intelliorder.order.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.service.IOrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
@CrossOrigin
@RequestMapping("/order/order")
@Api(tags = "点餐中订单功能的Controller")
public class OrderController {

    @Autowired
    private IOrderService orderService;

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

    @RequestMapping(value = "/updateOrderState", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改付款状态", notes = "需要输入数字为订单编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = 404, message = "更新失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String updateOrderState(int orderId) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = orderService.updateOrderState(orderId);
            if (result == 1) {
                map.put("status", "200");
                map.put("msg", "更新成功");
            } else {
                map.put("status", "404");
                map.put("msg", "更新失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

}
