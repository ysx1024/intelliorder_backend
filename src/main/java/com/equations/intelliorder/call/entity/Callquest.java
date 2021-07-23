package com.equations.intelliorder.call.entity;

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
 * @since 2021-07-16
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class Callquest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "callId", type = IdType.AUTO)
    private Integer callId;

    @TableField("deskId")
    private Integer deskId;

    @TableField("callMsg")
    private String callMsg;

    @TableField("callTime")
    private LocalDateTime callTime;

    @TableField("staffId")
    private Integer staffId;

    @TableField("callStatus")
    private Integer callStatus;

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    public void setCallMsg(String callMsg) {
        this.callMsg = callMsg;
    }

    public void setCallTime(LocalDateTime callTime) {
        this.callTime = callTime;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(Integer callStatus) {
        this.callStatus = callStatus;
    }
}
