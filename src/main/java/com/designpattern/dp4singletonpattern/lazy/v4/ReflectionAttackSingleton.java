package com.designpattern.dp4singletonpattern.lazy.v4;

import java.lang.reflect.Constructor;

/**
 * 使用反射进行单例破坏
 */
public class ReflectionAttackSingleton {
    public static void main(String[] args) {

        // 获取单例类对象
        Class<?> clazz = LazyInnerClassSingleton.class;
        try {
            // 通过反射获取私有的构造方法
            Constructor c = clazz.getDeclaredConstructor(null);
            // 强制访问
            c.setAccessible(true);
            // 暴力初始化 两次
            Object o1 = c.newInstance();
            Object o2 = c.newInstance();
            System.out.println(o1==o2); // 返回false 相当于有两个单例对象
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
