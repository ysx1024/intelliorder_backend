package com.equations.intelliorder.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
@TableName("orderinfo")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("orderId")
    private int orderId;

    @TableField("openId")
    private String openId;

    @TableField("orderTime")
    private LocalDateTime orderTime;

    @TableField("endTime")
    private LocalDateTime endTime;

    @TableField("deskId")
    private Integer deskId;

    @TableField("totalPrice")
    private Double totalPrice;

    @TableField("orderStatus")
    private Boolean orderStatus;


}
