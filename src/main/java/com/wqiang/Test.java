package com.wqiang;

import com.myspring.MyAnnotationConfigApplicationContext;
import com.wqiang.service.UserService;
import com.wqiang.service.UserServiceImpl;

/***
 * 总共有三个bean  UserService OrderService WqiangBeanPostProcessor
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // xxxx
        // 根据 配置类 创建容器
        MyAnnotationConfigApplicationContext applicationContext =
                                                        // 配置类
                new MyAnnotationConfigApplicationContext(ApplicationContextConfig.class);

        // System.out.println(applicationContext.getBean("userService"));
        // 这里要使用接口的 userService.testOrderService()
        // 使用 UserServiceImpl userService = (UserServiceImpl) applicationContext.getBean("userService");报错
        // 被代理的对象： UserServiceImpl 实现的接口：UserService
        // 代理对象 是 UserService接口的实现，但是已经不是UserServiceImpl类，所以不能使用UserServiceImpl类接收。

        // 从容器中取出 UserService接口的实现
        System.out.println("=====从容器中取出对象使用=====");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.testOrderService(); // 1. 代理逻辑 2. 业务逻辑
    }
}
