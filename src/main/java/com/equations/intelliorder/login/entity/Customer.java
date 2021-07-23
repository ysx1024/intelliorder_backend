package com.equations.intelliorder.login.entity;

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
 * @since 2021-07-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("openId")
    private String openId;

    @TableField("nickName")
    private String nickName;

    @TableField("deskId")
    private Integer deskId;

    @TableField("avataUrl")
    private String avataUrl;

    private String gender;

    @TableField("firstLoginTime")
    private LocalDateTime firstLoginTime;

    @TableField("lastLoginTime")
    private LocalDateTime lastLoginTime;

    @TableId("openId")
    private String code;

}
