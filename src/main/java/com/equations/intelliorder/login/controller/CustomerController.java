package com.equations.intelliorder.login.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.equations.intelliorder.login.entity.Customer;
import com.equations.intelliorder.login.service.ICustomerService;
import com.equations.intelliorder.utils.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-21
 */
@RestController
@CrossOrigin
@RequestMapping("/login/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    String appId = "wx5d09a57a36240a0c";
    String secret = "fa7113349649612f5b37f142db6afa33";

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String login(Customer user, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try{
            String code = user.getCode();
            if (code == null ) {
                map.put("code", 300);
                map.put("msg", "code不能为空!");
            } else {
                //微信接口服务,通过调用微信接口服务中jscode2session接口获取到openid和session_key
                String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
                String str = WeChatUtil.httpRequest(url, "GET", null); //调用工具类解密
                JSONObject jsonObject = JSONObject.parseObject(str);
                String openId = (String) jsonObject.get("openid");
                String nickName = user.getNickName();
                String avataUrl = user.getAvataUrl();
                String gender = user.getGender();
                int deskId = user.getDeskId();
                int result = customerService.undateCustomer(openId, nickName,deskId,avataUrl,gender);
                if (result == 1) {
                    session.setAttribute("openId", openId);
                    map.put("code", 200);
                    map.put("msg", "登录成功!");
                    map.put("userInfo", jsonObject);
                }
                if (result == 0) {
                    map.put("code", 201);
                    map.put("msg", "未登录成功!");
                }
            }
        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }
}