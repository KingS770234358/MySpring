package com.designpattern.dp4singletonpattern.hungry;

/**
 * 饿汉式单例模式在类加载的时候就立即初始化，并且创建单例对象。
 * 优点：没有加任何锁、执行效率比较高，用户体验比懒汉式单例模式更好；
 * 它绝对线程安全，在线程还没出现以前就实例化了，不可能存在访问安全问题。
 * 缺点：类加载的时候就初始化，不管用与不用都占着空间，浪费了内存。
 */
public class Test {
    public static void main(String[] args) {

        System.out.println(HungrySingleton.getInstance());
        System.out.println(HungrySingleton.getInstance());

        System.out.println(HungryStaticSingleton.getInstance());
        System.out.println(HungryStaticSingleton.getInstance());
    }
}
