package com.designpattern.dp4singletonpattern.register;

import java.io.Serializable;

/***
 * 反序列化后的单例对象会重新分配内存，即重新创建，违背了单例模式的初衷
 * [ObjectInputStream类的readObject()方法]
 * 增加readResolve（）方法返回实例解决了单例模式被破坏的问题，但是实际上实例化了两次，只不
 * 过新创建的对象没有被返回而已。如果创建对象的动作发生频率加快，就意味着内存分配开销也会随之增大，
 */
public class SeriableSingleton implements Serializable {
    private final static SeriableSingleton INSTANCE = new SeriableSingleton();
    // 只要有无参构造方法，在反序列的时候就会实例化
    private SeriableSingleton(){}
    public static SeriableSingleton getInstance(){
        return INSTANCE;
    }

    /**
     * 通过反射找到一个无参的 readResolve()法
     * @return
     */
    private Object readResovle(){
        return INSTANCE;
    }
}
