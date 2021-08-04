package com.equations.intelliorder.order.requestVo;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 后端返回json实体类
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */

@Data

public class OrderResVo {

    //点餐桌号
    private Integer deskId;

    //订单号
    private Integer orderId;


    //dishOrders列表
    private List<DishOrder> dishOrders;

    // 总价
    private Double totalPrice;
}
