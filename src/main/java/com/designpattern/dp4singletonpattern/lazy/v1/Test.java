package com.designpattern.dp4singletonpattern.lazy.v1;

/**
 * 步骤一：创建单例对象
 * if(lazy==null){
 *     lazy = new LazySimpleSingleton();
 * }
 *
 * 步骤二：接收单例对象
 * LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
 *
 * 两个线程同时进入步骤一的if语句块的情况下：
 * 如果线程1先跑完了剩下的所有代码，再是线程2跑完剩下所有代码，则会产生两个不同的单例对象，单例存在线程安全隐患。
 * 如果线程1先执行的步骤一，然后在步骤二执行之前等待；然后线程2执行步骤一，则线程1在接收单例对象的时候，自己创建的
 * 单例对象已经被线程2的单例对象覆盖，导致线程1和线程2在步骤二接收到的单例对象都是相同的单例对象，线程安全隐患依旧存在。
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
