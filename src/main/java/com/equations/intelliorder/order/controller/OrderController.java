package com.equations.intelliorder.order.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.service.IOrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.equations.intelliorder.utils.Alipay.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */
@Controller
@CrossOrigin
@RequestMapping("/order/order")
@Api(tags = "点餐中订单功能的Controller")
public class OrderController {

    @Autowired
    private IOrderService orderService;




    @RequestMapping(value = "/showOrder", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "页面渲染时返回查看未付款的订单列表", notes = "前台查看列表返回")
    @ApiResponses({
            @ApiResponse(code = 404, message = "请求失败"),
            @ApiResponse(code = 200, message = "请求成功")
    })
    public String showOrderList() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Order> orderList = orderService.showOrderList();
            map.put("status", "200");
            map.put("data", orderList);
        } catch (Exception exception) {
            map.put("status", "404");
            map.put("errorMsg", exception.getMessage());
        }
        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/updateOrderState", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改付款状态", notes = "需要输入数字为订单编号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "int")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "更新成功"),
            @ApiResponse(code = 404, message = "更新失败"),
            @ApiResponse(code = -1, message = "errorMsg")
    })
    public String updateOrderState(int orderId) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = orderService.updateOrderState(orderId);
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

    @RequestMapping(value="/toPay",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "前台结账", notes = "需要提供订单号")
    public   String   toPay (int orderId) {

        Order order = orderService.toPay(orderId);
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient("\t\n" +
                "https://openapi.alipaydev.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://localhost:8088/order");
        //在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl("http://localhost:8088/notify");
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + order.getOpenId() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":\"" + order.getTotalPrice() + "\"," +
                "    \"subject\":\"点餐结账\"," +
                "    \"body\":\"" + order.getDeskId() + "号桌进行结账\"," +
                "    \"passback_params\":" +
                "    \"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }" ); //填充业务参数
        AtomicReference<String> form = new AtomicReference<>("");
        try {
            //调用SDK生成表单
            form.set(alipayClient.pageExecute(alipayRequest).getBody());
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        return form.get();
    }


    public String getTradeNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }


    @RequestMapping(value="/phonePay",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "顾客手机结账", notes = "需要提供订单号")
    public   String   phonePay (int orderId) {

        Order order = orderService.toPay(orderId);
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, FORMAT,
                CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        //支付成功后进行网页回调的地址
        request.setReturnUrl("http://localhost:8088/order");
        //在公共参数中设置回跳和通知地址
        request.setNotifyUrl("http://localhost:8088/notify");
        request.setBizContent("{" +
                "\"subject\":\"点餐结账\"," +
                "\"body\":\"" + order.getDeskId() + "号桌点餐结账\"," +
                "\"quit_url\":\"http://www.taobao.com/product/113714.html\"," +   //用户付款中途退出返回商户网站的地址
                "\"out_trade_no\":\"" + order.getOpenId() + "\"," +
                "\"total_amount\"" + order.getTotalPrice() + "\"," +
                "\"seller_id\":\"2088102147948060\"," +
                "\"passback_params\":" +
                "\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "\"product_code\":\"QUICK_WAP_PAY\"," +
                "  }");
        AtomicReference<String> response = new AtomicReference<>("");
        try {
            response.set(alipayClient.pageExecute(request).getBody());//调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        return response.get();
    }

}
