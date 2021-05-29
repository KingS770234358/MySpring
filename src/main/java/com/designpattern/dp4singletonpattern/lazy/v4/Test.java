package com.designpattern.dp4singletonpattern.lazy.v4;

import com.designpattern.dp4singletonpattern.lazy.v3.ExecutorThread;

/**
 *
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