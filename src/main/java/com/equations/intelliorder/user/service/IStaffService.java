package com.equations.intelliorder.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.equations.intelliorder.user.entity.Staff;

import java.util.List;

/**
 * <p>
 * 服务类，业务逻辑的定义层
 * </p>
 *
 * @author equations
 * @since 2021-07-10
 */
public interface IStaffService extends IService<Staff> {

    List<Staff> showStaffList();//渲染时返回所有员工的信息列表

    Staff getStaffById(int staffId);//根据id查询员工

    List<Staff> getStaffByName(String name);//根据姓名查询员工

    List<Staff> getStaffByType(String staffType);//根据员工类型查询员工

    int updateStaff(int staffId, String phone, String account, String password, String staffType);//修改员工信息

    int addStaff(String name, String phone, String staffType);//增加员工信息

    int deleteById(int staffId);//物理删除员工信息

    Staff login(String account, String password);//员工登录

    int changePassword(int staffId, String oldPassword, String newPassword);//个人密码修改

    int changeAccount(int staffId, String account);//个人密码修改

    int changePhone(int staffId, String phone);//个人密码修改

    Staff showStaffInfo(int staffId);//个人信息展示
}
