package com.equations.intelliorder.queue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.queue.entity.Queuelist;
import com.equations.intelliorder.queue.mapper.QueuelistMapper;
import com.equations.intelliorder.queue.service.IQueuelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-17
 */
@Service
public class QueuelistServiceImpl extends ServiceImpl<QueuelistMapper, Queuelist> implements IQueuelistService {

    @Autowired
    private QueuelistMapper queuelistMapper;//通过字段注入自动创建mapper映射类


    @Override
    public int addQueue(String openId) {
        QueryWrapper<Queuelist> wrapper = new QueryWrapper<>();
        wrapper.like("openId", openId);
        List<Queuelist> queuelistList = queuelistMapper.selectList(wrapper);
        if (!(queuelistList == null)) {
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

    @Override
    public Queuelist showQueuelist(String openId) {
        QueryWrapper<Queuelist> wrapper = new QueryWrapper<>();
        wrapper.eq("openId", openId);
        wrapper.eq("queueStatus", 0);
        return queuelistMapper.selectOne(wrapper);
    }


    @Override
    public int deleteQueue() {
        return queuelistMapper.delete(new QueryWrapper<>());
    }

    @Override
    public List<Queuelist> showQueue() {

        //1)创建QueryWrapper对象
        QueryWrapper<Queuelist> wrapper = new QueryWrapper<>();
        wrapper.eq("queueStatus", 0);
        //2)执行查询
        return queuelistMapper.selectList(wrapper);
    }

}
