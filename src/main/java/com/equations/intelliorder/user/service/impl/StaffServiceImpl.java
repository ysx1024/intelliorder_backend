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
 * 服务实现类，业务逻辑的实现层
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
    public List<Staff> showStaffList() {
        //1)创建QueryWrapper对象
        QueryWrapper wrapper = new QueryWrapper();
        //通过id>=1的条件巧用ge方法返回所有员工列表信息
        wrapper.ge("id", 1);
        //2)执行查询
        return staffMapper.selectList(wrapper);
    }

    @Override
    public Staff getStaffById(int id) {
        //1)创建QueryWrapper对象，通过id找到需要操作的某行
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        //2)执行查询
        return staffMapper.selectOne(wrapper);
    }

    @Override
    public List<Staff> getStaffByName(String name) {
        //1)创建QueryWrapper对象，通过name寻找到需要操作的某行
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name", name);
        //2)执行查询
        return staffMapper.selectList(wrapper);
    }

    @Override
    public List<Staff> getStaffByType(String staffType) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("staffType", staffType);
        return staffMapper.selectList(wrapper);
    }

    @Override
    public int updateStaff(
            int id, String phone, String account,
            String password, String staffType) {
        //1)创建QueryWrapper对象，通过id找到需要操作的某行
        UpdateWrapper<Staff> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        //通过set将查找到的这行进行数据修改
        Staff staff = staffMapper.selectOne(wrapper);
        staff.setPhone(phone);
        staff.setAccount(account);
        staff.setPassword(password);
        staff.setStaffType(staffType);
        return staffMapper.update(staff, wrapper);
    }

    @Override
    public int addStaff(String name, String phone, String staffType) {
        //新建一个类，对staff类通过set操作插入表中
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
        // 直接通过方法进行删除操作
        return staffMapper.deleteById(id);
    }

    @Override
    public Staff login(String account, String password) {
        //员工登录实现
        QueryWrapper wrapper = new QueryWrapper();
        return staffMapper.selectOne(new QueryWrapper<Staff>().eq("account", account)
                .eq("password", password));
    }

    @Override
    public int changePassword(int id, String oldPassword, String newPassword) {
        //个人密码修改
        Staff result = staffMapper.selectById(id);
        UpdateWrapper<Staff> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        Staff staff = new Staff();
        staff.setPassword(newPassword);
        staffMapper.update(staff, wrapper);
        return staffMapper.update(staff, wrapper);
    }
}
