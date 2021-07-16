package com.equations.intelliorder.call.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCallId() {
        return callId;
    }

    public Integer getDeskId() {
        return deskId;
    }

    public String getCallMsg() {
        return callMsg;
    }

    public LocalDateTime getCallTime() {
        return callTime;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public Integer getCallStatus() {
        return callStatus;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
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

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public void setCallStatus(Integer callStatus) {
        this.callStatus = callStatus;
    }
}
