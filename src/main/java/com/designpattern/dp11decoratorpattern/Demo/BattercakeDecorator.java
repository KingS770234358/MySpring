package com.designpattern.dp11decoratorpattern.Demo;

/**
 * 装饰器类的抽象类
 * 继承于被装饰类的抽象类
 * 是可有可无的，具体可以根据业务模型来选择。
 */
public abstract class BattercakeDecorator extends Battercake{
    // 静态代理 委派
    private Battercake battercake; // 持有一个被装饰类的引用
    public BattercakeDecorator(Battercake battercake){
        this.battercake = battercake;
    }
    // 装饰器类扩展被装饰类的方法
    protected abstract void doSomething();

    @Override
    protected String getMsg() {
        return this.battercake.getMsg();
    }

    @Override
    protected int getPrice() {
        return this.battercake.getPrice();
    }
}

