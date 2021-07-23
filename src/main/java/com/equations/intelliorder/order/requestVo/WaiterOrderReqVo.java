package com.equations.intelliorder.order.requestVo;


import lombok.Data;

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

    // 总价
    private Integer totalPrice;

    //dishOrders列表
    private List<DishOrder> dishOrders;

}

