package com.designpattern.dp4singletonpattern.lazy.v2;

/**
 * 多线程调试模式下，如果线程1先进入synchronized修饰的方法，之后再切换到线程2运行，
 * 则线程2无法进入synchronized修饰的方法，会进入MONITOR阻塞状态，IDEA建议Resume 线程1，恢复线程1，先让线程1释放锁。
 * 优点：线程安全
 * 缺点：用synchronized加锁，意味着每次调用getInstance()方法都要加锁，就连单例对象已经存在可以直接返回的情况下也要加锁
 * 在线程数量比较多的情况下，如果CPU分配压力上升，则会导致大批线程阻塞，从而导致程序性能大幅下降。
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
