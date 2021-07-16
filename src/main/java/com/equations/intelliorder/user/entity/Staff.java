package com.equations.intelliorder.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 实体类，一一对应数据库中相应列的列名，数据库修改列时要对应修改此类
 * </p>
 *
 * @author equations
 * @since 2021-07-10
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private int staffId;//员工号id是主键，自动递增自动分配不可修改

    private String name;//员工姓名

    private String phone;//员工手机号

    private String account;//员工账号，初始为手机号

    private String password;//员工密码

    @TableField("staffType")
    private String staffType;//员工类别

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStaffType() {
        return staffType;
    }

    public void setStaffType(String staffType) {
        this.staffType = staffType;
    }
}
