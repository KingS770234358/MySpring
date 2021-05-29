package com.designpattern.dp4singletonpattern.ThreadLocalSingleton;


public class ExecutorThread implements Runnable {
    @Override
    public void run() {
        ThreadLocalSingleton singleton = ThreadLocalSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + " " + singleton);
    }
}
