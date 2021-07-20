package com.equations.intelliorder;

import com.equations.intelliorder.order.requestVo.WaiterOrderReqVo;
import com.equations.intelliorder.order.service.IOrderlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class IntelliorderApplicationTests {

    @Autowired
    private IOrderlistService orderlistService;

    @Test
    void contextLoads() {
    }

//    @Test
//    void testorder(){
//
//        WaiterOrderReqVo waiterOrderReqVo = new WaiterOrderReqVo();
//        waiterOrderReqVo.setDeskId(10);
//        List<WaiterOrderReqVo.DishOrder> dishOrders = new ArrayList<>();
//        dishOrders.add()
//        waiterOrderReqVo.setDishOrders(dishOrders);
//        orderlistService.waiterOrder(WaiterOrderReqVo waiterOrderReqVo)
//
//
//    }
}
