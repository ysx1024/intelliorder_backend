package com.equations.intelliorder.user.service;

import com.equations.intelliorder.user.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-10
 */
public interface IStaffService extends IService<Staff> {

        List<Staff> getStaffById(int id);//根据id查询员工

        List<Staff> getStaffByName(String name);//根据姓名查询员工

        int updateStaff(int id,String phone, String account,String password,String staffType);//修改员工信息

        int addStaff(String name,String phone, String account,String password,String staffType);//增加员工信息

        int deleteById(int id);//物理删除员工信息
}
