package com.designpattern.dp6proxypattern.dynamicproxy.cglibdynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Father implements MethodInterceptor {

    // 获取增强的代理对象
    public Object getProxyInstance(Class<?> clazz)throws Exception{
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz); // 设置被代理类
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 业务增强
        before();
        // 拦截器MethodInterceptor中就是由MethodProxy的invokeSuper（）方法调用代理方法的
        // 获取代理类对应的 FastClass，并执行代理方法。
        Object obj = methodProxy.invokeSuper(o, objects);
        after();
        return obj;
    }

    private void before(){
        System.out.println("增强前...");
    }
    private void after(){
        System.out.println("增强后...");
    }
}
