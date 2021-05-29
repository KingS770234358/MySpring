package com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxyPlus;

/**
 * 持久层操作类
 */
public class OrderDao {
    public int insert(Order order){
        System.out.println("OrderDao 插入order成功！");
        return 1;
    }
}
