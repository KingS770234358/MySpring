package com.designpattern.dp4singletonpattern.lazy.v2;

/**
 *  懒汉式单例模式 只有在外部用到的时候 内部类 才会加载
 */
public class LazySimpleSingleton {
    private LazySimpleSingleton(){}
    // 静态块 内存公共区域
    private static LazySimpleSingleton lazy = null;
    public synchronized static LazySimpleSingleton getInstance(){
        if(lazy==null){
            lazy = new LazySimpleSingleton();
        }
        return lazy;
    }
}
