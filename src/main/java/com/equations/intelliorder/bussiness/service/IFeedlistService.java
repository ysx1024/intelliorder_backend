package com.equations.intelliorder.bussiness.service;

import com.equations.intelliorder.bussiness.entity.Feedlist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-17
 */
public interface IFeedlistService extends IService<Feedlist> {

    int customerFeed(String openId, String feedText, int feedLevel);//顾客生成评价

    List<Feedlist> showFeedlistList();//展示未回复的评价

    int replyFeed(int feedId, String reply);//回复反馈

}
