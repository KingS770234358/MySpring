package com.designpattern.dp4singletonpattern.hungry;

public class HungrySingleton {
    // 先静态 后动态
    // 先属性 后方法
    // 先上后下
    private static final HungrySingleton hungrySingleton = new HungrySingleton();

    private HungrySingleton(){}

    public static HungrySingleton getInstance(){ // 静态方法！！
        return hungrySingleton;
    }
}
