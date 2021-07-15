package com.equations.intelliorder.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author equations
 * @since 2021-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orderlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("listId")
    private String listId;

    @TableField("orderTime")
    private LocalDateTime orderTime;

    @TableField("dishId")
    private String dishId;

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

    @TableField("staffId")
    private int staffId;

    public int getListStatus() {
        return listStatus;
    }

    public void setListStatus(int listStatus) {
        
        this.listStatus = listStatus;
    }

    public void setStaffId(int staffId) {
        this.staffId=staffId;
    }
}
