package com.equations.intelliorder.call.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.call.entity.Callquest;
import com.equations.intelliorder.call.service.ICallquestService;
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
 * @since 2021-07-16
 */

@Controller
@RestController
@CrossOrigin
@RequestMapping("/call/callquest")
@Api(tags = "呼叫模块的Controller")


public class CallquestController {

    @Autowired
    private ICallquestService callquestService;


    @RequestMapping(value = "/addCallquest", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "顾客通过手机呼叫服务", notes = "传入桌号和呼叫内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deskId", value = "桌号", required = true, dataType = "int"),
            @ApiImplicitParam(name = "callMsg", value = "呼叫内容", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "呼叫成功"),
            @ApiResponse(code = 404, message = "呼叫失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String addCallquest(String deskId, String callMsg) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = callquestService.addCallquest(Integer.parseInt(deskId), callMsg);
            if (result == 1) {
                map.put("status", "200");
                map.put("msg", "呼叫成功");
            } else {
                map.put("status", "404");
                map.put("msg", "呼叫失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/showCallquestList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回呼叫服务列表", notes = "渲染时即返回")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showCallquestList() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Callquest> callquestList = callquestService.showCallquestList();
            map.put("status", "200");
            map.put("data", callquestList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/receiveCallquest", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务员接收呼叫", notes = "呼叫id和接收服务员id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "呼叫id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "已有服务员接收服务中"),
            @ApiResponse(code = 200, message = "接收成功"),
            @ApiResponse(code = 404, message = "接收失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String receiveCallquest(int callId, int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Callquest callquest = callquestService.getById(callId);
            if (!(callquest.getCallStatus() == 0)) {
                map.put("status", "304");
                map.put("msg", "已有服务员接收呼叫服务中");
            } else {
                int result = callquestService.receiveCallquest(callId, staffId);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "接收成功");
                } else {
                    map.put("status", "404");
                    map.put("msg", "接收失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/completeCallquest", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "完成呼叫服务", notes = "参数是呼叫id和接收服务员id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "呼叫id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "staffId", value = "员工id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 304, message = "未接收不能完成呼叫服务"),
            @ApiResponse(code = 200, message = "完成成功"),
            @ApiResponse(code = 404, message = "完成失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String completeCallquest(int callId, int staffId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Callquest callquest = callquestService.getById(callId);
            if (!(callquest.getCallStatus() == 1) || !(staffId == callquest.getStaffId())) {
                map.put("status", "304");
                map.put("msg", "未接收不能完成呼叫服务");
            } else {
                int result = callquestService.completeCallquest(callId, staffId);
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "呼叫服务完成");
                } else {
                    map.put("status", "404");
                    map.put("msg", "呼叫服务失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

}
