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
}
