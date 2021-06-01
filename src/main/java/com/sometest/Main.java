package com.sometest;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 每个线程调用单例对象的成员方法，该成员方法中只存在局部变量，不存在单例的成员变量
 * 这种情况下，每个线程调用单例对象成员方法时，该方法中的局部变量保存在各线程各自的工作内存（栈）中
 * 方法局部变量不会发生在线程之间共享的情况。
 */
public class Main {
    public static void main(String[] args) {
        /*
        for (int j = 0; j<10; j++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SingletonLocalVar.print();
                }
            }).start();
        }
        */
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int j = 0; j<1000; j++) {
            executorService.submit(() -> {
                System.out.println(SingletonLocalVar.getInstance().hashCode());
                SingletonLocalVar.getInstance().print();
            });
        }
        executorService.shutdown(); // 最后要关闭才会停
    }
}
