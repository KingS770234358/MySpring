package com.designpattern.dp4singletonpattern.register.EnumSingleton;

public enum EnumSingleton {
    INSTANCE; // 枚举对象天生为单例 INSTANCE是一种EnumSingleton
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }

    private Object data; // 枚举对象实际持有的单例对象。
    public Object getData(){
        return data;
    }
    public void setData(Object data){
        this.data = data;
    }

}
