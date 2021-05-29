package com.designpattern.dp11decoratorpattern.Demo;

public class SausageBattercakeDecorator extends BattercakeDecorator  {

    public SausageBattercakeDecorator(Battercake battercake){
        super(battercake);
    }

    @Override
    protected void doSomething() {
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + " 1个香肠 ";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 2;
    }
}
