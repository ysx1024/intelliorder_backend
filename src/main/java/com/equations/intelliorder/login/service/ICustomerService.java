package com.equations.intelliorder.login.service;

import com.equations.intelliorder.login.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-21
 */
public interface ICustomerService extends IService<Customer> {



    //获取数据

    int undateCustomer(String openId,String nickName,int deskId,String avataUrl,String gender);//更新数据
}
