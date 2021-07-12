package com.equations.intelliorder.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.equations.intelliorder.user.entity.Staff;
import com.equations.intelliorder.user.mapper.StaffMapper;
import com.equations.intelliorder.user.service.IStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-10
 */


@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public List<Staff> getStaffById(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("id",id);
        List<Staff> staffList = staffMapper.selectList(wrapper);
        return staffList;
    }

    @Override
    public int updateStaff(
            int id, String phone, String account,
            String password, String staffType) {
        UpdateWrapper<Staff> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        Staff staff = new Staff();
        staff.setPhone(phone);
        staff.setAccount(account);
        staff.setPassword(password);
        staff.setStaffType(staffType);


        return staffMapper.update(staff,wrapper);
    }

    @Override
    public int deleteById(String id) {
        // Staff staff=new Staff();
        return staffMapper.deleteById(id);
    }
}
