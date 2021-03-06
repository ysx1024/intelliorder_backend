package com.equations.intelliorder.queue.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.queue.entity.Queuelist;
import com.equations.intelliorder.queue.service.IQueuelistService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-17
 */
@RestController
@CrossOrigin
@RequestMapping("/queue/queuelist")
public class QueuelistController {


    //创立排队序号静态变量与相关方法
    private static int signQueueNow = 0;
    @Autowired
    private IQueuelistService queuelistService;//通过字段注入自动创建业务类，调用IQueuelistService类

    public void setSignQueueNow(int queueNow) {
        signQueueNow = queueNow + 1;
    }


    @RequestMapping(value = "/addQueue", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "顾客通过手机叫号", notes = "扫码登录并点击叫号按钮")
    @ApiResponses({
            @ApiResponse(code = 304, message = "已经叫号"),
            @ApiResponse(code = 200, message = "叫号成功"),
            @ApiResponse(code = 404, message = "叫号失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String addQueue(String openId) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = queuelistService.addQueue(openId);
            if (result == 2) {
                map.put("status", "304");
                map.put("msg", "您已经叫号");
            } else {
                if (result == 1) {
                    map.put("status", "200");
                    map.put("msg", "叫号成功");
                } else {
                    map.put("status", "404");
                    map.put("msg", "叫号失败");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/changeQueue", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务员点击下一位", notes = "点击下一位")
    @ApiResponses({
            @ApiResponse(code = 200, message = "叫号更新成功"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String changeQueue() {
        Map<String, Object> map = new HashMap<>();
        try {
            this.setSignQueueNow(signQueueNow);
            queuelistService.changeQueue(signQueueNow);
            map.put("status", "200");
            map.put("queueNow", signQueueNow);
            map.put("msg", "叫号更新成功");
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/showQueuelist", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "顾客通过手机查看", notes = "进入查看页面")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查看成功"),
            @ApiResponse(code = 201, message = "准备就餐"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String showQueuelist(String openId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Queuelist result = queuelistService.showQueuelist(openId);
            int queue = result.getQueueCustomer();
            if (queue > signQueueNow) {
                map.put("status", "200");
                map.put("queueNow", signQueueNow);
                map.put("data", result);
            } else if (queue == signQueueNow) {
                map.put("status", "201");
                map.put("msg", "请您准备就餐");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/showQueue", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回所有叫号列表", notes = "渲染时即返回")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showQueue() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Queuelist> queueLists = queuelistService.showQueue();
            LinkedList queueCustomer = new LinkedList();
            for (Queuelist queuelist:queueLists){
                queueCustomer.add(queuelist.getQueueCustomer());
            }
            map.put("status", "200");
            map.put("queueNow", signQueueNow);
            map.put("data", queueCustomer);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/deleteQueue", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "前台点击清空", notes = "清空数据库")
    @ApiResponses({
            @ApiResponse(code = 200, message = "清空成功"),
            @ApiResponse(code = 201, message = "清空失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String deleteQueue() {
        Map<String, Object> map = new HashMap<>();
        try {

            int result = queuelistService.deleteQueue();
            if (result == 0) {
                this.setSignQueueNow(-1);
                map.put("status", "200");
                map.put("queueNow", signQueueNow);
                map.put("msg", "清空成功");
            } else {
                map.put("status", "201");
                map.put("msg", "清空失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

}