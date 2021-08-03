package com.equations.intelliorder.order.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.service.IOrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
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
@CrossOrigin(origins = "http://10.128.135.182:8080/")
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
    public String updateOrderState(String orderId) {
        Map<String, Object> map = new HashMap<>();
        try {
            int result = orderService.updateOrderState(Integer.parseInt(orderId));
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


    @RequestMapping(value = "/toPay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "前台结账", notes = "需要提供订单号")
    public String toPay(String orderId) {

        Order order = orderService.toPay(Integer.parseInt(orderId));
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient("\t\n" +
                "https://openapi.alipaydev.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, FORMAT,
                CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://10.128.135.182:8080/Reception/OrderList");
        //在公共参数中设置回跳和通知地址
        alipayRequest.setNotifyUrl("http://10.128.135.182:8088/order/order/updateOrderState");
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + getTradeNo() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":\"" + order.getTotalPrice() + "\"," +
                "    \"subject\":\"点餐结账\"," +
                "    \"body\":\"" + order.getDeskId() + "号桌进行结账\"," +
                "    \"passback_params\":" +
                "    \"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }"); //填充业务参数
        AtomicReference<String> form = new AtomicReference<>("");
        try {
            //调用SDK生成表单
            form.set(alipayClient.pageExecute(alipayRequest).getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form.get();
    }




    public String getTradeNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }


    @RequestMapping(value = "/phonePay", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "顾客手机结账", notes = "需要提供订单号")
    public String phonePay(String orderId) {

        Order order = orderService.toPay(Integer.parseInt(orderId));
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                APP_ID, APP_PRIVATE_KEY, FORMAT,
                CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        //支付成功后进行网页回调的地址
//        request.setReturnUrl("http://10.128.135.182:8080/Reception/OrderList");
//        //在公共参数中设置回跳和通知地址
//        request.setNotifyUrl("http://localhost:8088/notify");
        DecimalFormat df = new DecimalFormat("######0.00");
        request.setBizContent("{" +
                "\"subject\":\"点餐结账\"," +
//                "\"body\":\"" + order.getDeskId() + "号桌点餐结账\"," +
                "\"out_trade_no\":\"" + getTradeNo() + "\"," +
                "\"timeout_express\":\"90m\","+
                "\"total_amount\":\"" + df.format(order.getTotalPrice()) + "\"," +
                "\"product_code\":\"FACE_TO_FACE_PAYMENT\"" +
                "  }");
        AlipayTradePrecreateResponse response = null;
        String qrCode = "";
        Map<String, Object> map = new HashMap<>();
        try {
            response = alipayClient.execute(request);
            if (!response.isSuccess()) {
                map.put("生成支付宝订单失败:" ,response.getMsg());
            }
            qrCode = response.getQrCode();
//            map.put("orderId",orderId);
            map.put("Url",qrCode);

        } catch (Exception exception) {
            map.put("status", "-1");
            map.put("errorMsg", exception.getMessage());
        }
        // 封装支付信息 返回
        return JSON.toJSONString(map);
    }


}
