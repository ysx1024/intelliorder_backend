package com.equations.intelliorder.bussiness.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.equations.intelliorder.bussiness.entity.Bussinessdata;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author equations
 * @since 2021-07-19
 */
public interface BussinessdataMapper extends BaseMapper<Bussinessdata> {


    @Select("<script>"+
            "select IFNULL(max(DATE_FORMAT(orderTime,'%Y-%m')),0)"+
            "from orderinfo \n"+
            "</script>")
    String queryMaxOrderMonth();

    //按月求月销售和
    //注解Select执行sql语句
    @Select("<script>"+
            //订单总价求和
            "select IFNULL(sum(totalPrice),0)"+
            "from orderinfo \n"+
            //将数据库date日期转换成yyyy-mm与string类型的yearMonth进行查找
            "where DATE_FORMAT(orderTime,'%Y-%m')=#{max}"+
            "</script>")
    //写入double类型的方法查询前端传入年月的订单总价
    double queryTotalSum(@Param("max") String max);

    //获得最大菜品id
    @Select("<script>"+
            "select IFNULL(max(dishId),0)"+
            "from dish \n"+
            "</script>")
    int queryMaxDishId();

    //通过起始截止日期和菜品id查询菜品时间段内销售量和利润
    @Select("<script>"+
            "select IFNULL(sum(dishNum),0)"+
            "from orderlist \n"+
            //"where DATE_FORMAT(orderTime,'%Y-%m-%d')=#{monthDay} AND dishId=#{i}"+
            "where DATE_FORMAT(orderTime,'%Y-%m-%d %H:%i:%s') BETWEEN #{startDay} and #{endDay} AND dishId=#{i}"+
            "</script>")
    int queryTotalDishNum(@Param("startDay")String startDay,@Param("endDay")String endDay,int i);

}
