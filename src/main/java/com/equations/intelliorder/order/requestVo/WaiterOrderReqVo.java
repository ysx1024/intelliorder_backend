package com.equations.intelliorder.order.requestVo;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * <p>
 * 前端请求json实体类
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */

@Data
public class WaiterOrderReqVo {

    //点餐桌号
    private Integer deskId;

    //dishOrders列表
    private List<DishOrder> dishOrders;

    // 为上述列表准备的类、
    @Data
    public static class DishOrder {

        //菜品Id
        private Integer dishId;

        //该菜品数量
        private Integer dishNum;
    }
}
