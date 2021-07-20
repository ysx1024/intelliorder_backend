package com.equations.intelliorder.queue.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.queue.entity.Queuelist;
import com.equations.intelliorder.queue.service.IQueuelistService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-17
 */
@RestController
@RequestMapping("/queue/queuelist")
public class QueuelistController {


    @Autowired
    private IQueuelistService queuelistService;//通过字段注入自动创建业务类，调用IQueuelistService类

    private static int signQueueNow=0;

    public int getSignQueueNow(){
        return signQueueNow;}

    public void setSignQueueNow(int queueNow){
        signQueueNow=queueNow+1;
    }




    @RequestMapping(value = "/addQueue", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "顾客通过手机叫号", notes = "扫码登录并点击叫号按钮")
    @ApiResponses({
            @ApiResponse(code = 304, message = "已经叫号"),
            @ApiResponse(code = 200, message = "叫号成功"),
            @ApiResponse(code = 404, message = "叫号失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String addQueue(HttpSession session) {
        String openId = session.getAttribute("openId").toString();
        Map<String, Object> map = new HashMap<>();
        try {
            int result = queuelistService.addQueue(openId);
            if (result==2) {
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
            map.put("status", "200");
            map.put("queueNow", signQueueNow);
            map.put("msg","叫号更新成功");
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
    public String showQueuelist(HttpSession session) {
        String openId = session.getAttribute("openId").toString();
        Map<String, Object> map = new HashMap<>();
        try {
            Queuelist result = queuelistService.showQueuelist(openId);
            int queue = result.getQueueCustomer();
            if(queue>signQueueNow) {
                map.put("status", "200");
                map.put("queueNow", signQueueNow);
                map.put("data", result);
            }else if(queue==signQueueNow){
                map.put("status", "201");
                map.put("msg","请您准备就餐");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/deleteQueue", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "服务员点击清空", notes = "清空数据库")
    @ApiResponses({
            @ApiResponse(code = 200, message = "清空成功"),
            @ApiResponse(code = 201, message = "清空失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String deleteQueue() {
        Map<String, Object> map = new HashMap<>();
        try {
            this.setSignQueueNow(-1);
            int result = queuelistService.deleteQueue();
            if (result==0){
                map.put("status", "200");
                map.put("queueNow", signQueueNow);
                map.put("msg","清空成功");
            }else {
                map.put("status","201");
                map.put("msg","清空失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

}