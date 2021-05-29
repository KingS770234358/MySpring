package com.designpattern.dp11decoratorpattern.ExtendsDemo;

public class BattercakeWithEggAndSausage extends BattercakeWithEgg {
    @Override
    protected String getMsg() {
        return super.getMsg() + "1个香肠";
    }

    @Override
    public int getPrice() {
        return super.getPrice() + 2;
    }
}
