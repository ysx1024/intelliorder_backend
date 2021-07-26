package com.equations.intelliorder.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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


    public void setOrderStatus(boolean b) {
    }


    public Double getTotalPrice() {
        return  totalPrice;
    }

    public void setEndTime(LocalDateTime dataTime) {
    }
}
