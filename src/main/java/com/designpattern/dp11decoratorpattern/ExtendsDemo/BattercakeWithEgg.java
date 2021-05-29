package com.designpattern.dp11decoratorpattern.ExtendsDemo;

public class BattercakeWithEgg extends Battercake {
    @Override
    protected String getMsg() {
        return super.getMsg() + "1个鸡蛋";
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 1;
    }
}
