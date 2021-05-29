package com.designpattern.dp4singletonpattern.lazy.v2;

public class ExecutorThread implements Runnable {
    @Override
    public void run() {
        LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + " " + singleton);
    }
}
