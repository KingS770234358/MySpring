package com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxyPlus;

public class OrderService implements IOrderService {

    // 持有一个持久层操作类
    private OrderDao orderDao;
    public OrderService(){
        this.orderDao = new OrderDao();
    }
    @Override
    public int createOrder(Order order) {
        System.out.println("OrderService 调用orderDao创建订单");
        return orderDao.insert(order);
    }
}
