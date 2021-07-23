package com.equations.intelliorder.queue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Queuelist implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "queueCustomer", type = IdType.AUTO)
    private Integer queueCustomer;

    @TableField("openId")
    private String openId;

    @TableField("queueTime")
    private LocalDateTime queueTime;

    @TableField("queueStatus")
    private Integer queueStatus;


}
