package com.equations.intelliorder.bussiness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.bussiness.entity.Bussinessdata;
import com.equations.intelliorder.bussiness.mapper.BussinessdataMapper;
import com.equations.intelliorder.bussiness.service.IBussinessdataService;
import com.equations.intelliorder.dish.service.IDishService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-19
 */
@Service
public class BussinessdataServiceImpl extends ServiceImpl<BussinessdataMapper, Bussinessdata> implements IBussinessdataService {

    @Autowired
    private BussinessdataMapper bussinessdataMapper;//通过字段注入自动创建mapper映射类
    @Autowired
    private IDishService dishService;

    //每月月底10：15定时执行更新当月销售数据
    @Scheduled(cron = "0 15 10 L * ?")
    public void updateBussiness() {
        //查询最近一月时间
        String max = bussinessdataMapper.queryMaxOrderMonth();
        System.out.println(max);

        //查询最近一月总销售额
        double sum = bussinessdataMapper.queryTotalSum(max);
        //数据类型转换，string转date存入时间
        String month=max+"-01 00:00:00";
        System.out.println(month);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime yearMonth = LocalDateTime.parse(month, df);
        Bussinessdata bussinessdata = new Bussinessdata();
        //插入营业额数据日期
        bussinessdata.setStartDay(yearMonth);
        //插入月销售额
        bussinessdata.setDishProfit(sum);
        //将dishId定为1，代表月销售额
        bussinessdata.setDishId(1);
        bussinessdata.setDishName("月销售额");
        bussinessdataMapper.insert(bussinessdata);
    }

    /*测试每月更新当月销售额功能能否正常实现
    @Override
    public int testInsert(){
        //查询最近一月时间
        String max = bussinessdataMapper.queryMaxOrderMonth();
        System.out.println(max);

        //查询最近一月总销售额
        double sum = bussinessdataMapper.queryTotalSum(max);
        //数据类型转换，string转date存入时间
        String month=max+"-01 00:00:00";
        System.out.println(month);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime yearMonth = LocalDateTime.parse(month, df);
        Bussinessdata bussinessdata = new Bussinessdata();
        //插入营业额数据日期
        bussinessdata.setStartDay(yearMonth);
        //插入月销售额
        bussinessdata.setDishProfit(sum);
        //将dishId定为1，代表月销售额
        bussinessdata.setDishId(1);
        bussinessdata.setDishName("月销售额");
        return bussinessdataMapper.insert(bussinessdata);
    }*/

    @Override
    public List<Bussinessdata> queryTotal(String startMonth, String endMonth) {

        QueryWrapper<Bussinessdata> wrapper = new QueryWrapper<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime month1 = LocalDateTime.parse(startMonth, df);
        LocalDateTime month2 = LocalDateTime.parse(endMonth, df);
        wrapper.ge("startDay", month1);
        wrapper.le("startDay", month2);
        wrapper.eq("dishId", 1);
        return bussinessdataMapper.selectList(wrapper);
    }

    @Override
    public List<Bussinessdata> updateDishProfit(String startDay, String endDay) {
        //查询数据库中是否有所需日期的数据
        QueryWrapper<Bussinessdata> wrapper1 = new QueryWrapper<>();
        wrapper1.apply("date_format(startDay, '%Y-%m-%d %H:%i:%s') = {0}", startDay);
        wrapper1.apply("date_format(endDay, '%Y-%m-%d %H:%i:%s') = {0}", endDay);
        wrapper1.eq("dishId", 2);
        Bussinessdata data=bussinessdataMapper.selectOne(wrapper1);
        //判断有数据则直接传给前端，没有则先插入再传给前端
        if (data!=null && data.getDataId()>0){
            //排序利润降序
            QueryWrapper<Bussinessdata> wrapper = new QueryWrapper<>();
            //时间格式
            wrapper.apply("date_format(startDay, '%Y-%m-%d %H:%i:%s') = {0}", startDay);
            wrapper.apply("date_format(endDay, '%Y-%m-%d %H:%i:%s') = {0}", endDay);
            wrapper.orderByDesc("dishProfit");
            //获取前10条方便图标显示
            List<Bussinessdata> list = bussinessdataMapper.selectList(wrapper);
            if (list.size() > 10) {//判断list长度
                return list.subList(0, 10);//取前十条数据
            } else {
                return list;
            }
        }else {
            //获得最大菜品id进行下面的遍历求菜品利润
            int max = bussinessdataMapper.queryMaxDishId();
            for (int i = 2; i <= max; i++) {
                //按日查询不同菜品销售量和利润
                int dishNum = bussinessdataMapper.queryTotalDishNum(startDay, endDay, i);
                String dishName = dishService.getDishId(i).getDishName();
                double dishPrice = dishService.getDishId(i).getDishPrice();
                double costPrice = dishService.getDishId(i).getCostPrice();
                //菜品利润
                double dishProfit = dishNum * (dishPrice - costPrice);
                //插入一条营业额数据
                Bussinessdata bussinessdata = new Bussinessdata();
                bussinessdata.setDishNum(dishNum);
                bussinessdata.setDishProfit(dishProfit);
                bussinessdata.setDishId(i);
                bussinessdata.setDishName(dishName);
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime monthDay1 = LocalDateTime.parse(startDay, df);
                LocalDateTime monthDay2 = LocalDateTime.parse(endDay, df);
                //插入营业额数据日期
                bussinessdata.setStartDay(monthDay1);
                bussinessdata.setEndDay(monthDay2);
                bussinessdataMapper.insert(bussinessdata);
            }
            //排序利润降序
            QueryWrapper<Bussinessdata> wrapper = new QueryWrapper<>();
            //时间格式
            wrapper.apply("date_format(startDay, '%Y-%m-%d %H:%i:%s') = {0}", startDay);
            wrapper.apply("date_format(endDay, '%Y-%m-%d %H:%i:%s') = {0}", endDay);
            wrapper.orderByDesc("dishProfit");
            //获取前10条方便图标显示
            List<Bussinessdata> list = bussinessdataMapper.selectList(wrapper);
            if (list.size() > 10) {//判断list长度
                return list.subList(0, 10);//取前十条数据
            } else {
                return list;
            }
        }
    }

}


