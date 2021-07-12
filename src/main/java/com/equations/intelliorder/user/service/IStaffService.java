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




        List<Staff> getStaffById(int id);

        List<Staff> getStaffByName(String name);

        int updateStaff(int id,String phone, String account,String password,String staffType);

        int addStaff(String name,String phone, String account,String password,String staffType);

        int deleteById(int id);
}
