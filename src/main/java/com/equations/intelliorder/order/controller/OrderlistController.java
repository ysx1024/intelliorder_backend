package com.equations.intelliorder.order.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.service.IOrderlistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-14
 */
@Controller
@RestController
@CrossOrigin
@RequestMapping("/order/orderlist")
@Api(tags = "点餐功能的Controller")

public class OrderlistController {

    @Autowired
    private IOrderlistService orderlistService;//通过字段注入自动创建业务类，调用orderlistService类

    @RequestMapping(value = "/showOrderlist", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回待做订单菜品列表", notes = "厨师查看待做列表返回")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showOrderlistList() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Orderlist> orderlistList = orderlistService.showOrderlistList();
            map.put("status", "200");
            map.put("data", orderlistList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/receiveOrderlist",method = RequestMethod.POST)
    public  String receiveOrderlist(int listId,int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Orderlist orderlist = orderlistService.getById(listId);
            if (!(orderlist.getListStatus() == 0)) {
                map.put("status", "304");
                map.put("msg", "已有厨师接单制作中");
            } else {
                int result = orderlistService.receiveOrderlist(listId, staffId);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "接单成功");
                } else {
                    map.put("status", "404");
                    map.put("msg", "接单失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/completeOrderlist",method = RequestMethod.POST)
    public  String completeOrderlist(int listId,int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Orderlist orderlist = orderlistService.getById(listId);
            if (!(orderlist.getListStatus() == 1) || !(staffId == orderlist.getStaffId())) {
                map.put("status", "304");
                map.put("msg", "未接单不能完成制作");
            } else {
                int result = orderlistService.completeOrderlist(listId, staffId);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "制作完成");
                } else {
                    map.put("status", "404");
                    map.put("msg", "制作失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/serveList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回服务员上菜列表", notes = "服务员查看上菜列表")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String serveList() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Orderlist> orderlistList = orderlistService.serveList();
            map.put("status", "200");
            map.put("data", orderlistList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/receiveServe",method = RequestMethod.POST)
    public  String receiveServe(int listId,int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Orderlist orderlist = orderlistService.getById(listId);
            if (!(orderlist.getListStatus() == 2)) {
                map.put("status", "304");
                map.put("msg", "已有服务员接单送菜中");
            } else {
                int result = orderlistService.receiveOrderlist(listId, staffId);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "接单成功");
                } else {
                    map.put("status", "404");
                    map.put("msg", "接单失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/completeServe",method = RequestMethod.POST)
    public  String completeServe(int listId,int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Orderlist orderlist = orderlistService.getById(listId);
            if (!(orderlist.getListStatus() == 3) || !(staffId == orderlist.getStaffId())) {
                map.put("status", "304");
                map.put("msg", "未接上菜不能完成上菜");
            } else {
                int result = orderlistService.completeServe(listId, staffId);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "上菜完成");
                } else {
                    map.put("status", "404");
                    map.put("msg", "上菜失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

}
