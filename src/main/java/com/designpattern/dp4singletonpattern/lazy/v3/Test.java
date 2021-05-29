package com.designpattern.dp4singletonpattern.lazy.v3;


/**
 *
 * 使用双检锁方式的单例模式
 * 优点：当单例对象已经存在时，可以不走synchronized语句块，尽量避免加锁
 * 缺点：用到 synchronized 关键字总归要上锁，对程序性能还是存在一定影响的。
 */
public class Test {
    public static void main(String[] args) {

        Thread t1 = new Thread(new ExecutorThread());
        Thread t2 = new Thread(new ExecutorThread());
        t1.start();
        t2.start();

        System.out.println("End...");
    }
}