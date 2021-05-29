package com.designpattern.dp6proxypattern.staticproxyPlus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  1.DynamicDataSourceEntry 负责切换数据源
 *  2.OrderService 实现IOrderSerice接口，负责调用OrderDao(被代理类)
 *  3.OrderDao 负责具体的订单Order的增删改查
 *  4.OrderServiceStaticProxy 实现IOrderService接口是代理类，持有DynamicDataSourceEntry和OrderService的引用
 *    负责调用DynamicDataSourceEntry来切换数据源，然后调用OrderService执行业务
 *  5.Order订单类
 */
public class Test {
    public static void main(String[] args) {

        // 创建订单
        Order order = new Order();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = sdf.parse("2017/02/01");
            order.setCreateTime(date.getTime());

            // 创建代理类 负责 确定数据库，并调用OrderService.createOrder()方法将新订单插入数据库
            IOrderService orderServiceStaticProxy = new OrderServiceStaticProxy(new OrderService());
            orderServiceStaticProxy.createOrder(order);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
