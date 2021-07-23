package com.equations.intelliorder.queue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.equations.intelliorder.queue.entity.Queuelist;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-17
 */
public interface IQueuelistService extends IService<Queuelist> {

    int addQueue(String openId);//顾客叫号

    Queuelist showQueuelist(String openId);//顾客手机渲染

    void changeQueue();//服务员点击下一位

    int deleteQueue();//一键清空
}
