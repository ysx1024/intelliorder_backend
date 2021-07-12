package com.equations.intelliorder.user.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.user.entity.Staff;
import com.equations.intelliorder.user.service.IStaffService;
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
 * @since 2021-07-10
 */
@Controller
@RestController
@CrossOrigin
@RequestMapping("/user/staff")
@Api(value="员工信息管理接口",tags = {"员工信息管理的Controller"})

public class StaffController {

    private final IStaffService staffService;

    public StaffController(IStaffService staffService) {
        this.staffService = staffService;
    }


    @RequestMapping(value = "/getStaffById",method = RequestMethod.GET)

    @ResponseBody
    @ApiOperation(value="根据员工ID检索员工列表",notes = "员工列表查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="ID",required = true,dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code=20001,message="请求失败"),
            @ApiResponse(code=200,message="请求成功")
    })

    public String getStaffById(int id) {
        Map<String,Object> map =new HashMap<>();
        try {
            List<Staff> staffList = staffService.getStaffById(id);
            map.put("status", "200");
            map.put("data", staffList);
        }catch (Exception exception){
            map.put("status", "20001");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/getStaffByName",method = RequestMethod.GET)
        public String getStaffByName(String name) {
            Map<String,Object> map =new HashMap<>();
        try {
            List<Staff> staffList = staffService.getStaffByName(name);
            map.put("status", "200");
            map.put("data", staffList);
        }catch (Exception exception){
            map.put("status", "20001");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/updateStaff",method = RequestMethod.POST)
    public String updateStaff(
            int id, String phone, String account,
            String password, String staffType){
        Map<String,Object> map = new HashMap<>();
        try {
            int result=staffService.updateStaff(id,phone,account,password,staffType);
            if(result==1){
                map.put("status", "200");
                map.put("msg", "更新成功");
            }else {
                map.put("status", "404");
                map.put("msg", "更新失败");
            }
        }catch (Exception exception){
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/addStaff",method = RequestMethod.POST)
    public String addStaff(
            String name, String phone, String account,
            String password, String staffType) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = staffService.addStaff(name, phone, account, password, staffType);
            if (result == 1) {
                map.put("status", "200");
                map.put("msg", "添加成功");
            } else {
                map.put("status", "404");
                map.put("msg", "添加失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
    public String deleteById(int id){
        Map<String,Object> map = new HashMap<>();
        try {
            int result=staffService.deleteById(id);
            if(result==1){
                map.put("status", "200");
                map.put("msg", "删除成功");
            }else {
                map.put("status", "404");
                map.put("msg", "没有找到数据");
            }
        }catch (Exception exception){
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }
}
