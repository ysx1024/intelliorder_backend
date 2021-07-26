package com.equations.intelliorder.bussiness.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author equations
 * @since 2021-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Bussinessdata implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dataId", type = IdType.AUTO)
    private Integer dataId;

    @TableField("startDay")
    private LocalDateTime startDay;

    @TableField("endDay")
    private LocalDateTime endDay;

    @TableField("dishProfit")
    private Double dishProfit;

    @TableField("dishId")
    private Integer dishId;

    @TableField("dishName")
    private String dishName;

    @TableField("dishNum")
    private int dishNum;

    public void setDishNum(int dishNum) { this.dishNum=dishNum;
    }

    public void setDishProfit(Double dishProfit) {
        this.dishProfit = dishProfit;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setStartDay(LocalDateTime startDay) { this.startDay=startDay; }

    public void setEndDay(LocalDateTime endDay) { this.endDay=endDay; }

}
