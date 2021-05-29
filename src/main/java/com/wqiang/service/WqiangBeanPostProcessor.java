package com.wqiang.service;

import com.myspring.Annotation.Component;
import com.myspring.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/***
 * BeanPostProcess类对所有的bean的创建都有效
 * 这里处于方便， 将其实现类一起放入service包下 方便扫描
 */
@Component("wqiangBeanPostProcessor") // BeanPostProcessor也要作为bean注册到spring容器中
public class WqiangBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前...");
        // 对指定类型的bean进行操作
        if("userService".equals(beanName)){
            ((UserServiceImpl)bean).setName("自定义 特定bean的 BeanPostProcessor");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("初始化后...");
        // 对指定类型的bean进行操作
        if("userService".equals(beanName)){
            // 生成指定类型的代理对象 实现AOP
            // 使用当前BeanPostProcessor的类加载器进行加载
            Object proxyInstance = Proxy.newProxyInstance(WqiangBeanPostProcessor.class.getClassLoader(),
                    bean.getClass().getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                            // 先执行代理逻辑
                            System.out.println("方法前代理逻辑");
                            // 再执行业务逻辑
                            Object rtn = method.invoke(bean, objects);
                            // 最后执行代理逻辑
                            System.out.println("方法后代理逻辑");
                            return rtn;
                        }
                    });
            return proxyInstance;
        }
        return bean;
    }
}
