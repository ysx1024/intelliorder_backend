package com.equations.intelliorder;

import com.equations.intelliorder.order.service.IOrderlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
