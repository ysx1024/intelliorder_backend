package com.equations.intelliorder.bussiness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equations.intelliorder.bussiness.entity.Feedlist;
import com.equations.intelliorder.bussiness.mapper.FeedlistMapper;
import com.equations.intelliorder.bussiness.service.IFeedlistService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public int customerFeed(String openId, String feedText, int feedLevel) {
        Feedlist feedlist = new Feedlist();
        feedlist.setOpenId(openId);
        feedlist.setFeedText(feedText);
        feedlist.setFeedLevel(feedLevel);
        feedlist.setFeedTime(LocalDateTime.now());
        return feedlistMapper.insert(feedlist);
    }

    @Override
    public List<Feedlist> showFeedlistList() {
        QueryWrapper<Feedlist> wrapper = new QueryWrapper<>();
        //展示回复不为空的反馈
        wrapper.isNull("reply");
        return feedlistMapper.selectList(wrapper);
    }

    @Override
    public int replyFeed(int feedId, String reply) {
        QueryWrapper<Feedlist> wrapper = new QueryWrapper<>();
        wrapper.eq("feedId", feedId);
        Feedlist feedlist = feedlistMapper.selectOne(wrapper);
        feedlist.setReply(reply);
        return feedlistMapper.update(feedlist,wrapper);
    }
}
