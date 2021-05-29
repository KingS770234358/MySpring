package com.designpattern.dp11decoratorpattern.Demo;

/**
 * 装饰器模式：
 * 指在不改变原有对象的基础上，将功能附加到对象上，
 * 提供了比继承更有弹性的方案（扩展原有对象的功能），属于结构型模式。
 * *最本质的特征是将原有类的附加功能抽离出来，简化原有类的逻辑
 *
 * 适用场景：
 * （1）扩展一个类的功能或给一个类添加附加职责。
 * （2）动态给一个对象添加功能，【这些功能可以再动态地撤销】。
 *
 * 优点：
 * （1）是继承的有力补充，且比继承灵活，可以在不改变原有对象的情况下【动态】地给一个对象扩展功能，即插即用。
 * （2）使用不同的装饰类及这些装饰类的排列组合，可以实现不同的效果。
 * （3）装饰者模式完全符合开闭原则。
 * 缺点：
 * （1）会出现更多的代码、更多的类，增加程序的复杂性。
 * （2）动态装饰时，多层装饰会更复杂。
 *
 * 装饰器模式和适配器模式对比
 *           装饰器模式                                   适配器模式
 * 形式    特殊的适配器模式，有层级关系              适配器与被适配类之间没有层级关系
 * 定义    装饰器和被装饰类实现同一个抽象类或接口     适配器与被适配类之间没有必然的联系，通常采用继承
 *         主要目的：拓展之后保留OOP关系            或代理的形式进行包装
 * 关系    满足is-a的关系                          满足has-a的关系
 * 功能    注重覆盖、扩展                          注重兼容、转换
 * 设计    前置考虑（前期）                        后置考虑（后期）
 */
public class Test {
    public static void main(String[] args) {
        Battercake battercake;
        battercake = new BaseBattercake(); // 最简单的煎饼
        System.out.println(battercake.getMsg() + ", 总价格：" + battercake.getPrice());

        // 加鸡蛋 (装饰器类也是 被装饰类一种，扩展了被装饰类的功能 可以直接返回给被装饰类接受)
        battercake = new EggBattercakeDecorator(battercake);
        System.out.println(battercake.getMsg() + ", 总价格：" + battercake.getPrice());

        // 加香肠
        battercake = new SausageBattercakeDecorator(battercake);
        System.out.println(battercake.getMsg() + ", 总价格：" + battercake.getPrice());

        // 再加鸡蛋
        battercake = new EggBattercakeDecorator(battercake);
        System.out.println(battercake.getMsg() + ", 总价格：" + battercake.getPrice());

    }
}
