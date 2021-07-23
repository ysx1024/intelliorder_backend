package com.equations.intelliorder.bussiness.controller;


import com.alibaba.fastjson.JSON;
import com.equations.intelliorder.bussiness.entity.Feedlist;
import com.equations.intelliorder.bussiness.service.IFeedlistService;
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
 * @since 2021-07-17
 */
@Controller
@RestController
@CrossOrigin
@RequestMapping("/bussiness/feedlist")
@Api(tags = "管理员经营状况的Controller")
public class FeedlistController {

    @Autowired
    private IFeedlistService feedlistService;//通过字段注入自动创建业务类，调用IFeedlistService类

    @RequestMapping(value = "/customerFeed", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "顾客发布评价", notes = "需要评价内容与评价等级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "顾客ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "feedText", value = "评价内容", required = true, dataType = "String"),
            @ApiImplicitParam(name = "feedLevel", value = "评价等级", required = true, dataType = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "评价失败"),
            @ApiResponse(code = 200, message = "评价成功"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String customerFeed(String openId, String feedText, int feedLevel) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = feedlistService.customerFeed(openId, feedText, feedLevel);
            if (result == 1) {
                map.put("status", "200");
                map.put("msg", "评价成功");
            } else {
                map.put("status", "404");
                map.put("msg", "评价失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/showFeedlistList", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回所有顾客反馈", notes = "渲染时即返回")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showFeedlistList() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Feedlist> feedlistList = feedlistService.showFeedlistList();
            map.put("status", "200");
            map.put("data", feedlistList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/replyFeed", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据反馈id回复反馈", notes = "反馈id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedId", value = "反馈ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "reply", value = "回复评价", required = true, dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "回复失败"),
            @ApiResponse(code = 200, message = "回复成功")
    })
    public String replyFeed(int feedId, String reply) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = feedlistService.replyFeed(feedId, reply);
            if (result == 1) {
                map.put("status", "200");
                map.put("msg", "回复成功");
            } else {
                map.put("status", "404");
                map.put("msg", "回复失败");
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }
}
