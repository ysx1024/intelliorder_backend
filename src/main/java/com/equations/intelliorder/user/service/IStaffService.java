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




        List<Staff> getStaffById(String id);

        int updateStaff(
                String id,String phone, String account,
                String password,String staffType,String dishType);

        int deleteById(String id);
}
