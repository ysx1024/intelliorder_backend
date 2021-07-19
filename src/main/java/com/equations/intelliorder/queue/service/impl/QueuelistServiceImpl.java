package com.equations.intelliorder.queue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equations.intelliorder.queue.entity.Queuelist;
import com.equations.intelliorder.queue.mapper.QueuelistMapper;
import com.equations.intelliorder.queue.service.IQueuelistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-17
 */
@Service
public class QueuelistServiceImpl extends ServiceImpl<QueuelistMapper, Queuelist> implements IQueuelistService {

    @Autowired
    private QueuelistMapper queuelistMapper;//通过字段注入自动创建mapper映射类


    private static int signQueueNow=0;


    public void setSignQueueNow(int queueNow){
        signQueueNow=queueNow+1;
    }

    @Override
    public int addQueue(String openId) {
        QueryWrapper<Queuelist> wrapper = new QueryWrapper<>();
        wrapper.like("openId",openId);
        List<Queuelist> queuelistList = queuelistMapper.selectList(wrapper);
        if(!(queuelistList==null)) {
            for (Queuelist list : queuelistList)
                if (list.getQueueStatus() == 0)
                    return 2;
        }
        Queuelist queuelist = new Queuelist();
        queuelist.setOpenId(openId);
        LocalDateTime dataTime = LocalDateTime.now();
        queuelist.setQueueTime(dataTime);
        queuelist.setQueueStatus(0);
        return queuelistMapper.insert(queuelist);
    }
}
