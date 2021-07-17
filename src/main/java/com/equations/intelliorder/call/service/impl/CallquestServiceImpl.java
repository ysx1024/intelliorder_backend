package com.equations.intelliorder.call.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.equations.intelliorder.call.entity.Callquest;
import com.equations.intelliorder.call.mapper.CallquestMapper;
import com.equations.intelliorder.call.service.ICallquestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.equations.intelliorder.order.entity.Orderlist;
import com.equations.intelliorder.order.mapper.OrderlistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-16
 */
@Service
public class CallquestServiceImpl extends ServiceImpl<CallquestMapper, Callquest> implements ICallquestService {

    @Autowired
    private CallquestMapper callquestMapper;//通过字段注入自动创建mapper映射类

    @Override
    public List<Callquest> showCallquestList() {
        QueryWrapper<Callquest> wrapper = new QueryWrapper<>();
        wrapper.lt("callStatus", 2);
        return callquestMapper.selectList(wrapper);
    }

    @Override
    public int receiveCallquest(int callId,int staffId){
        UpdateWrapper<Callquest> wrapper = new UpdateWrapper<>();
        wrapper.eq("callId",callId);
        Callquest callquest = callquestMapper.selectOne(wrapper);
        callquest.setCallStatus(1);
        callquest.setStaffId(staffId);
        return callquestMapper.update(callquest,wrapper);
    }

    @Override
    public  int completeCallquest(int callId,int staffId){
        UpdateWrapper<Callquest> wrapper = new UpdateWrapper<>();
        wrapper.eq("callId",callId);
        Callquest callquest = callquestMapper.selectOne(wrapper);
        callquest.setCallStatus(2);
        return callquestMapper.update(callquest,wrapper);
    }


}
