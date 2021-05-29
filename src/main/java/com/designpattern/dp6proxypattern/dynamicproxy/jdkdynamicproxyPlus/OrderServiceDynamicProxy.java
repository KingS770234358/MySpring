package com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxyPlus;

import com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxy.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class OrderServiceDynamicProxy implements InvocationHandler {

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private Object orderService;

    public Object getProxyInstance(Object target){
        this.orderService = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        before(objects[0]);
        Object obj = method.invoke(orderService, objects);
        after();
        return obj;
    }

    private void before(Object order){
        System.out.println("Proxy before method...");
        try {
            Long time = (Long)order.getClass().getMethod("getCreateTime").invoke(order);
            Integer dbRouter = Integer.valueOf(yearFormat.format(new Date(time)));
            System.out.println("动态代理类自动分配到【DB_" + dbRouter + "】数据源处理数据");
            DynamicDataSourceEntry.set(dbRouter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void after(){
        System.out.println("Proxy after method...");
    }
}
