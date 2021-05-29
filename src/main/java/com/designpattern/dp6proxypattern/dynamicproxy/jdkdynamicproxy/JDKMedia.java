package com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKMedia implements InvocationHandler {

    // 保存被代理对象的引用
    private Object target;

    // 获取代理对象
    public Object getProxyInstance(Object target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        before();
        Object obj = method.invoke(this.target, objects);
        after();
        return obj;
    }

    private void before(){
        System.out.println("增强前操作...");
    }

    private void after(){
        System.out.println("增强后操作...");
    }
}
