package com.equations.intelliorder.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.login.entity.Customer;
import com.equations.intelliorder.login.mapper.CustomerMapper;
import com.equations.intelliorder.login.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-21
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;



    @Override
    public int undateCustomer(String openId, String nickName, int deskId, String avataUrl, String gender) {
        UpdateWrapper<Customer> wrapper = new UpdateWrapper<>();
        if (wrapper.eq("openId",openId)==null){
            Customer customer = new Customer();
            customer.setOpenId(openId);
            customer.setNickName(nickName);
            customer.setDeskId(deskId);
            customer.setAvataUrl(avataUrl);
            customer.setGender(gender);
            LocalDateTime dataTime = LocalDateTime.now();
            customer.setFirstLoginTime(dataTime);
            return customerMapper.insert(customer);
        }else {
            Customer customer = customerMapper.selectOne(wrapper);
            customer.setNickName(nickName);
            customer.setDeskId(deskId);
            customer.setAvataUrl(avataUrl);
            customer.setGender(gender);
            LocalDateTime dataTime = LocalDateTime.now();
            customer.setLastLoginTime(dataTime);
            return customerMapper.update(customer,wrapper);
        }
    }
}
