package com.equations.intelliorder.bussiness.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.bussiness.entity.Bussinessdata;
import com.equations.intelliorder.bussiness.entity.Feedlist;
import com.equations.intelliorder.bussiness.service.IBussinessdataService;
import com.equations.intelliorder.bussiness.service.IFeedlistService;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.service.IOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-19
 */
@Controller
@RestController
@CrossOrigin
@RequestMapping("/bussiness/bussinessdata")
public class BussinessdataController {

    @Autowired
    private IBussinessdataService bussinessdataService;//通过字段注入自动创建业务类，调用IBussinessdataService类


    @RequestMapping(value = "/queryTotal", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据年月更新月份销售额供前端折线图", notes = "前端传参年月应该是String类型yyyy-mm格式")
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String queryTotal(String startMonth,String endMonth) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Bussinessdata> result = bussinessdataService.queryTotal(startMonth,endMonth);
            map.put("status", "200");
            map.put("data", result);
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/updateDishProfit", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据年月更新月份销售额供前端饼状图", notes = "前端传参年月应该是String类型yyyy-mm格式")
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String updateDishProfit(String startDay,String endDay) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Bussinessdata> list=bussinessdataService.updateDishProfit(startDay,endDay);
//            试图传数组json
//            List dishName=new ArrayList();
//            List dishNum=new ArrayList();
//            List pie=new ArrayList();
//            for (int i=0;i<list.size();i++){
//                dishName.add(list.get(i).getDishName());
//                dishNum.add(list.get(i).getDishNum());
//                pie.add(list.get(i).getDishName());
//                pie.add(list.get(i).getDishProfit());
//
//            }
//            map.put("y-data",dishName);
//            map.put("series_data",dishNum);
//            map.put("data",pie);
            map.put("status", "200");
            map.put("data",list);
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


}
