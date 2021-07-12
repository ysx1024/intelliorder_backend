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
    private StaffMapper staffMapper;//通过字段注入自动创建mapper映射类


    @Override
    public List<Staff> getStaffById(int id) {
        //1)创建QueryWrapper对象
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("id",id);
        //2)执行查询
        List<Staff> staffList = staffMapper.selectList(wrapper);
        return staffList;
    }

    @Override
    public List<Staff> getStaffByName(String name) {
        //1)创建QueryWrapper对象
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name", name);
        //2)执行查询
        List<Staff> staffList = staffMapper.selectList(wrapper);
        return staffList;
    }

    @Override
    public int updateStaff(
            int id, String phone, String account,
            String password, String staffType) {
        //1)创建QueryWrapper对象
        UpdateWrapper<Staff> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        //对staff类进行set基本操作
        Staff staff = new Staff();
        staff.setPhone(phone);
        staff.setAccount(account);
        staff.setPassword(password);
        staff.setStaffType(staffType);
        return staffMapper.update(staff,wrapper);
    }

    @Override
    public int addStaff(
        String name, String phone, String account,
        String password, String staffType){
            Staff staff = new Staff();

            staff.setName(name);
            staff.setPhone(phone);
            staff.setAccount(phone);
            staff.setPassword("123456");
            staff.setStaffType(staffType);
            return staffMapper.insert(staff);
    }

    @Override
    public int deleteById(int id) {
        // Staff staff=new Staff();
        return staffMapper.deleteById(id);
    }
}
