package com.equations.intelliorder.order.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.dish.entity.Dish;
import com.equations.intelliorder.dish.service.IDishService;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.requestVo.CustomerOrderReqVo;
import com.equations.intelliorder.order.requestVo.WaiterOrderReqVo;
import com.equations.intelliorder.order.service.IOrderlistService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
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

    @Autowired
    private IDishService dishService;


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
            for (Orderlist orderlist:orderlistList){
                int dishId = orderlist.getDishId();
                Dish dish = dishService.getDishId(dishId);
                String dishName = dish.getDishName();
                orderlist.setDishName(dishName);
            }
            map.put("status", "200");
            map.put("data", orderlistList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/receiveOrderlist", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "厨师接单", notes = "参数为订单id与员工id'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listId", value = "订单id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "已有厨师接单制作中"),
            @ApiResponse(code = 200, message = "接单成功"),
            @ApiResponse(code = 404, message = "接单失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String receiveOrderlist(int listId, int staffId) {
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

    @RequestMapping(value = "/completeOrderlist", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "完成菜品", notes = "参数为订单id与员工id'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listId", value = "订单id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "未接单不能完成制作"),
            @ApiResponse(code = 200, message = "制作成功"),
            @ApiResponse(code = 404, message = "制作失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String completeOrderlist(int listId, int staffId) {
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


    @RequestMapping(value = "/receiveServe", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务员上菜接单", notes = "参数为订单id与员工id'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listId", value = "订单id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "已有服务员接单送菜中"),
            @ApiResponse(code = 200, message = "接单成功"),
            @ApiResponse(code = 404, message = "接单失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String receiveServe(int listId, int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Orderlist orderlist = orderlistService.getById(listId);
            if (!(orderlist.getListStatus() == 2)) {
                map.put("status", "304");
                map.put("msg", "已有服务员接单送菜中");
            } else {
                int result = orderlistService.receiveServe(listId, staffId);
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

    @RequestMapping(value = "/completeServe", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务员上菜完成", notes = "参数为订单id与员工id'")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listId", value = "订单id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "未接上菜不能完成上菜"),
            @ApiResponse(code = 200, message = "接单成功"),
            @ApiResponse(code = 404, message = "接单失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String completeServe(int listId, int staffId) {
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

    @RequestMapping(value = "/showOrderInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "前台请求详细信息", notes = "通过订单号请求")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showOrderInfo(String orderId) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Orderlist> orderlists = orderlistService.showOrderInfo(Integer.parseInt(orderId));
            for (Orderlist orderlist:orderlists){
                int dishId = orderlist.getDishId();
                Dish dish = dishService.getDishId(dishId);
                String dishName = dish.getDishName();
                orderlist.setDishName(dishName);
            }
            map.put("status", "200");
            map.put("data", orderlists);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/waiterOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务员点餐", notes = "点餐系列操作完成后传给打包好的json数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "waiterOrderReqVo", value = "请求json实体类",
                    required = true, dataType = "WaiterOrderReqVo"),
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String waiterOrder(@RequestBody WaiterOrderReqVo waiterOrderReqVo) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = orderlistService.waiterOrder(waiterOrderReqVo);
            if (result == 1) {
                map.put("status", "200");
                map.put("data", "下单成功");
            } else {
                map.put("status", "404");
                map.put("data", "下单失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/customerOrder", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "顾客点餐", notes = "点餐系列操作完成后传给打包好的json数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "customerOrderReqVo", value = "请求json实体类",
                    required = true, dataType = "CustomerOrderReqVo"),
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String customerOrder(@RequestBody CustomerOrderReqVo customerOrderReqVo, String openId) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Orderlist> orderlist = orderlistService.customerOrder(customerOrderReqVo, openId);
            map.put("status", "200");
            map.put("data", orderlist);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

}
