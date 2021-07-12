package com.equations.intelliorder.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author equations
 * @since 2021-07-10
 */
@Data
//@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private String name;

    private String phone;

    private String account;

    private String password;

    @TableField("staffType")
    private String staffType;


}
