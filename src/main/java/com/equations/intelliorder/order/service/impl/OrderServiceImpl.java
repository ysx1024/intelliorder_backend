package com.equations.intelliorder.order.service.impl;

import com.equations.intelliorder.order.entity.Order;
import com.equations.intelliorder.order.mapper.OrderMapper;
import com.equations.intelliorder.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author equations
 * @since 2021-07-15
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
