package com.designpattern.dp4singletonpattern.lazy.v4;

/**
 *  静态内部类单例模式（懒汉式单例模式 ）
 *  只有在外部用到的时候 内部类 才会加载
 *  兼顾 饿汉单例 的内存浪费问题 和 懒汉单例的synchronized性能问题
 */
public class LazyInnerClassSingleton {
    // 使用LazySimpleSingleton的时候，默认会先初始化内部类，不使用的话内部类是不会加载的
    private LazyInnerClassSingleton(){
        // 在构造函数中增加判断 防止【反射破坏单例模式】
        if(LazyHolder.LAZY!=null){
            throw new RuntimeException("不允许创建多个单例");
        }
    }

    // static 单例的内存空间共享  final保证方法不会被重写重载
    public static final LazyInnerClassSingleton getInstance(){
        // 在返回结果之前，才会（一定会）先加载内部类
        return LazyHolder.LAZY;
    }
    // static 内存空间共享 持有单例对象的静态内部类 在用到的时候才会被加载
    private static class LazyHolder{
        // static 单例的内存空间共享  final保证方法不会被重写重载
        private static final LazyInnerClassSingleton LAZY = new LazyInnerClassSingleton();
    }
}
