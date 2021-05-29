package com.designpattern.dp4singletonpattern.ThreadLocalSingleton;

public class Test {
    /**
     * ThreadLocal将所有的对象全部放在ThreadLocalMap中，为每个线程都提供一个对象
     * 实际上是以空间换时间来实现线程隔离的。
     */
    public static void main(String[] args) {
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
        System.out.println(ThreadLocalSingleton.getInstance());

        Thread t1 = new Thread(new ExecutorThread());
        Thread t2 = new Thread(new ExecutorThread());
        t1.start();
        t2.start();
    }
}
