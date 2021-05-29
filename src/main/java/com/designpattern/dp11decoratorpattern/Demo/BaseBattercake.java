package com.designpattern.dp11decoratorpattern.Demo;

/**
 * 被装饰类的具象类
 */
public class BaseBattercake extends Battercake {
    @Override
    protected String getMsg() {
        return "煎饼";
    }

    @Override
    protected int getPrice() {
        return 5;
    }
}
