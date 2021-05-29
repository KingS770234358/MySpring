package com.designpattern.dp6proxypattern.dynamicproxy.cglibdynamicproxy;

/**
 * CGLib 是通过动态继承目标对象实现动态代理的，因此代理的目标对象不需要实现任何接口
 */
public class Kid {
    public void eat(){
        System.out.println("吃...");
    }
}
