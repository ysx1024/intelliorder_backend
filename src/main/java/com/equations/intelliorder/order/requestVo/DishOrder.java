package com.equations.intelliorder.order.requestVo;

import lombok.Data;

// 为上述列表准备的类、
@Data
public class DishOrder {

    //菜品Id
    private Integer dishId;

    //菜品名称
    private String dishName;

    //该菜品数量
    private Integer dishNum;
}
