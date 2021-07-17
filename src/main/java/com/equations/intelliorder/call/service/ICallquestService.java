package com.equations.intelliorder.call.service;

import com.equations.intelliorder.call.entity.Callquest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author equations
 * @since 2021-07-16
 */
public interface ICallquestService extends IService<Callquest> {

    List<Callquest> showCallquestList();//展示待做呼叫服务

    int receiveCallquest(int callId,int staffId);//服务员接收呼叫服务中

    int completeCallquest(int callId,int satffId);//服务员完成呼叫服务

}
