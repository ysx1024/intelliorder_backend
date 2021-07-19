package com.equations.intelliorder.bussiness.entity;

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
 * @since 2021-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Feedlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("feedId")
    private String feedId;

    @TableField("openId")
    private String openId;

    @TableField("feedTime")
    private LocalDateTime feedTime;

    @TableField("feedText")
    private String feedText;

    @TableField("feedLevel")
    private Integer feedLevel;

    private String reply;


    public void setReply(String reply) {
        this.reply = reply;
    }
}
