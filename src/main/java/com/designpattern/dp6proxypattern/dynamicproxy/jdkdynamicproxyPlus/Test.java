package com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxyPlus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Test {
    public static void main(String[] args) {

        Order order = new Order();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = sdf.parse("2018/02/01");
            order.setCreateTime(date.getTime());

            // 创建代理类 负责 确定数据库，并调用OrderService.createOrder()方法将新订单插入数据库
            IOrderService orderServiceDynamicProxy
                    = (IOrderService) new OrderServiceDynamicProxy().getProxyInstance(new OrderService());
            orderServiceDynamicProxy.createOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
