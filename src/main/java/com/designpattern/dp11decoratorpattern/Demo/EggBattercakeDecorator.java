package com.designpattern.dp11decoratorpattern.Demo;

/**
 * 装饰器类的具象类
 * 鸡蛋装饰器类：只负责给煎饼加上1个鸡蛋
 */
public class EggBattercakeDecorator extends BattercakeDecorator {

    public EggBattercakeDecorator(Battercake battercake){
        super(battercake);
    }
    @Override
    protected void doSomething() {
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + " 1个鸡蛋 ";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 1;
    }
}
