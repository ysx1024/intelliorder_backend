package com.equations.intelliorder.bussiness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.equations.intelliorder.bussiness.entity.Bussinessdata;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-19
 */
public interface IBussinessdataService extends IService<Bussinessdata> {

    //按月查询月销售
    List<Bussinessdata> queryTotal(String startMonth, String endMonth);

    //按日期更新和查询排名前10的菜品销量和利润
    List<Bussinessdata> updateDishProfit(String startDay, String endDay);

}
