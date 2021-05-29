package com.designpattern.dp4singletonpattern.ThreadLocalSingleton;

public class ThreadLocalSingleton {

    private ThreadLocalSingleton(){}

    /**
     * ThreadLocal不能保证其创建的对象是全局唯一的
     * 但是能保证在单个线程中是唯一的，天生是线程安全的。
     */
    private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstance =
            new ThreadLocal<ThreadLocalSingleton>(){
                @Override
                protected ThreadLocalSingleton initialValue(){
                    return new ThreadLocalSingleton();
                }
            };
    public static ThreadLocalSingleton getInstance(){
        return threadLocalInstance.get(); // 第一次get触发initialValue
    };
}
