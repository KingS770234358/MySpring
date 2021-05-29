package com.wqiang.service;

import com.myspring.Annotation.Autowired;
import com.myspring.Annotation.Component;
import com.myspring.Annotation.ComponentScan;
import com.myspring.Annotation.Scope;
import com.myspring.Aware.BeanNameAware;
import com.myspring.InitializingBean;

/**
 * 用户提供的用户服务类
 */
@Component("userService")
// 没有显式声明作用域，默认是单例模式
@Scope("singleton")
public class UserServiceImpl implements BeanNameAware, InitializingBean, UserService {

    private String name;

    @Autowired
    private OrderService orderService;

    private String beanName; // BeanNameAware 获取自己在容器中的beanName

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void testOrderService(){
        System.out.println("业务逻辑：用户持有的orderService" + orderService);
    }

    // 实现BeanNameAware接口的方法
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("用户获得了自己在容器中的beanName：" + beanName);
    }

    // 实现InitializingBean接口的方法
    @Override
    public void afterPropertySet() throws Exception {
        // Spring提供的初始化机制（应用层面）检验bean对象
        System.out.println("初始化...");
    }
}
