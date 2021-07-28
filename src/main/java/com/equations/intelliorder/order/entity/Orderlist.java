package com.equations.intelliorder.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-07-14
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class Orderlist implements Serializable{

    private static final long serialVersionUID = 1L;

    @TableId("listId")
    private String listId;

    @TableField("orderId")
    private int orderId;

    @TableField("orderTime")
    private LocalDateTime orderTime;

    @TableField("dishId")
    private Integer dishId;

    @TableField("dishNum")
    private Integer dishNum;

    @TableField("openId")
    private String openId;

    @TableField("deskId")
    private Integer deskId;

    @TableField("dishPrice")
    private Double dishPrice;

    @TableField("listStatus")
    private int listStatus;

    @TableField("null")
    private String dishName;

    @TableField("staffId")
    private int staffId;

    public int getListStatus() {
        return listStatus;
    }

    public void setListStatus(int listStatus) {
        this.listStatus = listStatus;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishName() {
        return dishName;
    }
}
