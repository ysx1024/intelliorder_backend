package com.equations.intelliorder.dish.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.dish.entity.Dish;
import com.equations.intelliorder.dish.service.IDishService;
import com.equations.intelliorder.user.entity.Staff;
import io.swagger.annotations.*;
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
 * @since 2021-07-13
 */
@Controller
@RestController
@CrossOrigin
@RequestMapping("/dish/dish")
@Api(tags = "菜单管理的Controller")

public class DishController {

    @Autowired
    private IDishService dishService;//通过字段注入自动创建业务类，调用IDishServer类


    @RequestMapping(value = "/getDishId",method = RequestMethod.GET)//url地址和请求方法类型
    @ResponseBody
    @ApiOperation(value="根据菜单ID检索菜品",notes = "输入菜品ID必须是有效的数字")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dishId",value="菜品id",required = true,dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code=20001,message="请求失败"),
            @ApiResponse(code=200,message="请求成功")
    })
    public String getDishId(int dishId) {

        Map<String,Object> map =new HashMap<>();//创建hashmap用来存储返回列表并转成json数据
        try {
            Dish dish = dishService.getDishId(dishId);
            map.put("status", "200");
            map.put("data", dish);
        } catch (Exception exception) {
            map.put("status", "20001");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/getDishName", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据菜品名模糊检索菜单列表", notes = "需要输入菜品名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dishName", value = "菜品名", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 20001, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String getDishName(String dishName) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Dish> dishList = dishService.getDishName(dishName);
            map.put("status", "200");
            map.put("data", dishList);
        } catch (Exception exception) {
            map.put("status", "20001");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/getDishType",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="根据菜品类别检索菜单列表",notes = "需要输入菜品类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name="dishType",value="菜品类别",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=20001,message="请求失败"),
            @ApiResponse(code=200,message="请求成功")
    })
    public String getDishType(String dishType) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Dish> dishList = dishService.getDishType(dishType);
            map.put("status", "200");
            map.put("data", dishList);
        } catch (Exception exception) {
            map.put("status", "20001");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/updateDishState", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改上下架状态", notes = "需要输入数字为菜单编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dishId", value = "菜品id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = 404, message = "更新失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String addStaff(int dishId) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = dishService.updateDishState(dishId);
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
