package com.designpattern.dp11decoratorpattern.ExtendsDemo;

/**
 * 如果用户需要一个加2个鸡蛋、加1根香肠的煎饼，用我们现在的类结构是创建不出来的，也无法自动计算出价格，
 * 除非再创建一个类做定制。
 * 如果需求再变，一直做定制显然是不科学的。
 * 传统的继承方式无法应对多变的需求。
 */
public class Test {

    public static void main(String[] args) {

        Battercake battercake = new Battercake();
        System.out.println(battercake.getMsg() + ", 总价格：" + battercake.getPrice());

        BattercakeWithEgg battercakeWithEgg = new BattercakeWithEgg();
        System.out.println(battercakeWithEgg.getMsg() + ", 总价格：" + battercakeWithEgg.getPrice());

        BattercakeWithEggAndSausage battercakeWithEggAndSausage = new BattercakeWithEggAndSausage();
        System.out.println(battercakeWithEggAndSausage.getMsg() + ", 总价格：" + battercakeWithEggAndSausage.getPrice());
    }
}
