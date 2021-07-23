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

//import com.equations.intelliorder.order.entity.Orderlist;
//import com.equations.intelliorder.order.service.IOrderlistService;

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

    //应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    String APP_ID= "2021000117693467";
    //// 商户私钥，您的PKCS8格式RSA2私钥
    String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXGOXMQqbBqlJqYzjEp3aq/EnLMqz/8OFnIpfQnLo0n7iWijCveyeh+v4z2nJxt+ASieZzgddJgZtzlbLsYUhvkHZl6fTRAM3MaJo15hpqn4a18uIx/F8EGBAZOchStmkxhs5WdDVOU4AjNC0DpW09ou+c6mMfLtmVg7DIFeartFS8C9IEGuIguEEuHQekny6pvraFjYoNfWpwSy/8F/AGim3bm8ab2WqbwFxiBJuvd0OX1otcyHuMKwWlYBRmck7QVBZZhTgsrKNE6lgGk6tR58wBJRmCh1YKeNhtBfQm5F01HRgjjYoVzYC+TKidF13M5nk3OXYCfjTZHEj4MJexAgMBAAECggEAOPzs/yNMXdLtjq6ppJI80bSh6H/OfCHQAeQsOkRCfsKXDkBEqIrcod1Wrxv++iEN5DAauQoJtvGpPekCOOFgPqPP5BRff8y70hLJxvZolDpixpE6+vtiotqoh7shcQDXoBRmEnl1+XrI4340VCG6l/qyQQ6uH/69lgS7UlvgSnX7+sjpN4lcCQoob6rQNtTObrjDAx4w75p9mmT4uKn2nE8D2nAsgd+LOLOFb09J5Sm7k/9VOrzHEvaLxjGUMiIoK1lUpJQz5kBXtJPOp1OCLUa8U34xc0Exa6Dr9NDl9q4/xR7sBmvBgVnDiCzwnwFpHJdOoEWIGmVnKjo0SuqKWQKBgQDwb4QtKCpjZHDnWghMMOtVgLaXY5DFf+iUj/4kfVxM1l3xFXp2/Cnz4q++IO1DS6nd7SrkWgiF6t+z0haT6qb6hCVR2Ov6D+IMMsJB5Pw/r/sfYm3uCnptahzjzI6nLisGwYFagZedPPIcYCEtGbTpHAUOqJZVbitZrbmdc+1rIwKBgQCg4N8uCn9sdj5twbyI5MSNFXyDrZW8zusNyzquWXuT4MLzHd9z2fkcM6P+kGq/JUWMocEUC/JWSFqOp1bmS0zJVbdVMCijZcNXPh2yN7D3LBiHemDd8YeBfBC6V2EhBYXWwisGomXczaNFB1qjotkqHy0nSJpMo1iee3iPFf65GwKBgC9GXgDbAuqkFycGJteETuFy+3Ps2cxbiAmd/mSCWaGqDLKYfInkrwMLMW763mv69p1yQpq/sSlTs0SSLGpNGyPq08OyGUQD13h6ioOWWJrVKVVUbzT8znGxCZko4WuxwjgD2EFJddwu3ZQCusM5aL7lCYGGWHjXvqJVbEevQaFtAoGAO2w0I15vSJG4Z3QK/ol5bB9GmaCxUUcgnTy21RVqRunbOE+NbM5TbC5K4clYFHhg4xwQJCZxgZtCD7URrQidOTS2/flG2WvKJlxkPaLCc9nOyaPTScdf5Nz9lMaXssnGcNfRaqbrdw5hwyp/OTf6fMt9MyhnDe272vYCRt+58YcCgYEAvuCT68O8ZTUfxJQDzqXLX19C5IuddjSZCaDvJztt22SVD55WNdgwu7kFVmQn70i58omZwk0GLYS4cT9VieoSR1KBDPSd1Qfofk5Mi9lZzLmuY/N3Kd3wNhnO322fdbkJJvSydJnDkTm/WY6nJlGKe0G14KZSBIUHPqDyYpAWe8Q=";
    String FORMAT="json";
    String CHARSET="utf-8";
    //付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    String ALIPAY_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlUYDyaDpCwAQ+7tw1XJg9rpd1xbviVpxqV/8rtlAOYuVYmXRdlr2rmZUMfkF+ShRAlsHx3ya9KKjIbixCreHLI4mOqBurZQPW63wpW5GrpXPrx2Y8ahdVsrAxEBf2+rPO+MhRB3RebX20NuV39BS2x1Ft2cRpoEISnc02S2bSRjn3ZS7BVJG1AtqZ5akJ8WV5hilvXJILLf/y4ET7NTs+SSVD3JPwxnMvEkuq84mNnCv6L6V6bOzWpeznRAKkxefxRSrX9404NQH7BdvCkC57TS8VyV7WtGi8QrIeXRkwPMVSLIpcIU2ENrZvcdhdOwHUrDzw9ju26OB0cu0pXyQpQIDAQAB";
    String SIGN_TYPE="RSA2";




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

//    @RequestMapping(value = "/setDesk", method = RequestMethod.POST)
//    @ResponseBody
//    @ApiOperation(value = "设定桌号", notes = "服务员点餐前先设定桌号")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "deskId", value = "桌号", required = true, dataType = "int")
//    })
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "设定成功"),
//            @ApiResponse(code = 404, message = "设定失败"),
//            @ApiResponse(code = -1, message = "errorMsg")
//    })
//    public String setDesk(int deskId, HttpSession session) {
//        Map<String, Object> map = new HashMap<>();
//        try {
////            session.setAttribute("deskId", deskId);
//            int result = orderService.setDesk(deskId);
//            int orderId = orderService.getOrderByDeskId(deskId);
//            session.setAttribute("orderId", orderId);
//            if (result == 1) {
//                map.put("status", "200");
//                map.put("data", orderId);
//            } else {
//                map.put("status", "404");
//                map.put("msg", "设定失败");
//            }
//        } catch (Exception exception) {
//            map.put("status", "-1");
//            map.put("errorMsg", exception.getMessage());
//        }
//        return JSON.toJSONString(map);
//    }

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
    public   String   toPay (int orderId){

        Order order = orderService.toPay(orderId);
        AlipayClient alipayClient =  new DefaultAlipayClient( "\t\n" +
                "https://openapi.alipaydev.com/gateway.do" , APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);  //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest =  new AlipayTradePagePayRequest(); //创建API对应的request
        alipayRequest.setReturnUrl( "http://localhost:8088/order" );
        alipayRequest.setNotifyUrl( "http://localhost:8088/notify" ); //在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent( "{"  +
                "    \"out_trade_no\":\""+order.getOpenId()+"\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":\"" +order.getTotalPrice()+"\","+
                "    \"subject\":\"点餐结账\","  +
                "    \"body\":\""+order.getDeskId()+"号桌进行结账\","  +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
                "    \"extend_params\":{"  +
                "    \"sys_service_provider_id\":\"2088511833207846\""  +
                "    }" +
                "  }" ); //填充业务参数
        String form= "" ;
        try  {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

//    @RequestMapping(value="/pay")
//    public String toPay(){
//        return "pay"; //进入支付页面
//    }
//
//    @RequestMapping(value="/order")
//    public String toOrder(){
//        return "order";
//    }
    public String getTradeNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }


    @RequestMapping(value="/phonePay",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "顾客手机结账", notes = "需要提供订单号")
    public   String   phonePay (int orderId){

        Order order = orderService.toPay(orderId);
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setReturnUrl( "http://localhost:8088/order" );//支付成功后进行网页回调的地址
        request.setNotifyUrl( "http://localhost:8088/notify" );//在公共参数中设置回跳和通知地址
        request.setBizContent("{" +
                "\"subject\":\"点餐结账\"," +
                "\"body\":\""+order.getDeskId()+"号桌点餐结账\"," +
                "\"quit_url\":\"http://www.taobao.com/product/113714.html\"," +   //用户付款中途退出返回商户网站的地址
                "\"out_trade_no\":\""+order.getOpenId()+"\"," +
                "\"total_amount\""+order.getTotalPrice()+"\"," +
                "\"seller_id\":\"2088102147948060\"," +
                "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "\"product_code\":\"QUICK_WAP_PAY\"," +
                "  }");
       String response = "";
        try {
            response = alipayClient.pageExecute(request).getBody();//调用SDK生成表单
        }  catch  (AlipayApiException e) {
            e.printStackTrace();
        }
        return response;
    }

}
