package com.designpattern.dp6proxypattern.staticproxyPlus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderServiceStaticProxy implements IOrderService {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private IOrderService orderService;

    public OrderServiceStaticProxy(IOrderService orderService){
        this.orderService = orderService;
    }

    @Override
    public int createOrder(Order order) {
        before(order);
        orderService.createOrder(order);
        after();
        return 0;
    }
    public void before(Order order){ // 新增订单order时 需要确定要插入的数据 才能调用orderDao.createOrder()方法
        System.out.println("增强前操作");
        Long time = order.getCreateTime();
        // 根据订单的年份确定数据源
        Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("静态代理类自动分配到：【DB_" + dbRouter + "】数据源处理数据");
        DynamicDataSourceEntry.set(dbRouter);
    }
    public void after(){
        System.out.println("增强后操作");
    }
}
