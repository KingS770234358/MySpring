package com.designpattern.dp9templatepattern.demo;

/**
 * 模板模式(模板方法模式)
 * 指定义一个算法的骨架(方法的固定执行顺序)，并允许子类为一个或者多个步骤提供实现。
 * 使得子类可以在不改变算法结构的情况下，重新定义算法的某些步骤，属于行为型设计模式。
 *
 * 适用场景
 * (1)一次性实现一个算法的不变部分，并将可变的行为留给子类来实现。
 * (2)各子类中公共的行为被提取出来并集中到一个公共的父类中，从而避免代码重复。
 *
 * 优点：
 * （1）将相同处理逻辑的代码(不变的行为)放到抽象父类中，去除子类的重复代码，提高代码的复用性，符合开闭原则。
 * （2）将不同的代码放到不同的子类中，通过对子类的扩展增加新的行为，可以提高代码的扩展性。
 * 缺点：
 * （1）每个抽象类都需要一个子类来实现，导致了类的数量增加，间接地增加了系统的复杂性。
 * （2）因为继承关系自身的缺点，如果父类添加新的抽象方法，所有子类都要改一遍。
 */
public class Test {
    public static void main(String[] args) {

        System.out.println("---Java架构师课程---");
        NetworkCourse javaCourse = new JavaCourse();
        javaCourse.createCourse();

        System.out.println("---大数据课程---");
        NetworkCourse bigDataCourse = new BigDataCourse(true);
        bigDataCourse.createCourse();
    }
}
