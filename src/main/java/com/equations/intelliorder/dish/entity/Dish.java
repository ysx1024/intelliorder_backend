package com.equations.intelliorder.dish.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author equations
 * @since 2021-07-13
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dishId", type = IdType.AUTO)
    private int dishId;

    @TableField("dishName")
    private String dishName;

    @TableField("dishType")
    private String dishType;

    @TableField("dishPrice")
    private double dishPrice;

    @TableField("dishImage")
    private String dishImage;

    @TableField("dishDesc")
    private String dishDesc;

    @TableField("costPrice")
    private double costPrice;

    @TableField("dishState")
    private boolean dishState;

    
}
