package com.equations.intelliorder.dish.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.dish.entity.Dish;
import com.equations.intelliorder.dish.service.IDishService;
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

}