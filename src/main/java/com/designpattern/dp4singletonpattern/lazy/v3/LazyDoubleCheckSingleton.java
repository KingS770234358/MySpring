package com.designpattern.dp4singletonpattern.lazy.v3;

/**
 *  双检锁单例模式
 *  懒汉式单例模式 只有在外部用到的时候 内部类 才会加载
 */
public class LazyDoubleCheckSingleton {
    private LazyDoubleCheckSingleton(){}
    // 静态块 内存公共区域
    // lazy要使用volatile关键字 禁止指令重排序
    private volatile static LazyDoubleCheckSingleton lazy = null;
    // 尽量不加锁，只在lazy==null时加锁，减少加锁。
    // 在单例对象不为空的情况下就不用加锁，可以直接返回单例对象。
    public static LazyDoubleCheckSingleton getInstance(){
        if(lazy==null){
            synchronized (LazyDoubleCheckSingleton.class){
                if(lazy==null){
                    // 1.分配内存 2.初始化对象 3.设置lazy指向分配的内存区域
                    // lazy要使用volatile关键字 禁止指令重排序
                    lazy = new LazyDoubleCheckSingleton();
                }
            }
        }
        return lazy;
    }
}
