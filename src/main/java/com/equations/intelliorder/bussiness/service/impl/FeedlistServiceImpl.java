package com.equations.intelliorder.bussiness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equations.intelliorder.bussiness.entity.Feedlist;
import com.equations.intelliorder.bussiness.mapper.FeedlistMapper;
import com.equations.intelliorder.bussiness.service.IFeedlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.user.entity.Staff;
import com.equations.intelliorder.user.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-17
 */
@Service
public class FeedlistServiceImpl extends ServiceImpl<FeedlistMapper, Feedlist> implements IFeedlistService {

    @Autowired
    private FeedlistMapper feedlistMapper;//通过字段注入自动创建mapper映射类

    @Override
    public List<Feedlist> showFeedlistList() {
        QueryWrapper<Feedlist> wrapper = new QueryWrapper<>();
        //展示回复不为空的反馈
        wrapper.isNull("reply");
        return feedlistMapper.selectList(wrapper);
    }

    @Override
    public  int replyFeed(int feedId,String reply){
        QueryWrapper<Feedlist> wrapper = new QueryWrapper<>();
        wrapper.eq("feedId", feedId);
        Feedlist feedlist = feedlistMapper.selectOne(wrapper);
        feedlist.setReply(reply);
        return feedlistMapper.update(feedlist,wrapper);
    }
}
