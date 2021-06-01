package com.sometest;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
