package com.sometest;

import com.designpattern.dp4singletonpattern.register.EnumSingleton.EnumSingleton;

public enum SingletonLocalVar {
    INSTANCE; // 枚举对象天生为单例 INSTANCE是一种EnumSingleton
    public static SingletonLocalVar getInstance(){
        return INSTANCE;
    }

    private Object data; // 枚举对象实际持有的单例对象。
    public Object getData(){
        return data;
    }
    public void setData(Object data){
        this.data = data;
    }

    public void print(){
        int i = 1;
        i += 1;
        System.out.println(i);
    }
}
