package com.designpattern.dp4singletonpattern.lazy.v4;

import com.designpattern.dp4singletonpattern.lazy.v2.LazySimpleSingleton;

public class ExecutorThread implements Runnable {
    @Override
    public void run() {
        LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + " " + singleton);
    }
}
