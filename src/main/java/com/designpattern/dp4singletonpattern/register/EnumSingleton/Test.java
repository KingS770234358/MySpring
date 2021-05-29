package com.designpattern.dp4singletonpattern.register.EnumSingleton;

import java.io.*;

/**
 * Java 反编译工具 Jad（下载地址： https：//varaneckas.com/jad/）
 *
 * 线程安全：枚举式单例模式在静态代码块中就给INSTANCE进行了赋值，是饿汉式单例模式的实现。
 *
 * 序列化破坏：ObjectInputStream的readObject0（）方法，调用了readEnum（）方法，枚举类型其实通过
 * 类名和类对象类找到一个唯一的枚举对象。因此，枚举对象不可能被类加载器加载多次。序列化不会破坏该方法的单例
 *
 * 反射破坏：java.lang.Enum只有一个protected类型的构造方法，没有无参的构造方法；使用反射强制访问构造方法构造枚举对象则
 * “Cannot reflectively create enum objects”，在 newInstance（）方法中做了强制性的判断，如果修饰符是
 * Modifier.ENUM枚举类型，则直接抛出异常。即不能用反射来创建枚举类型，反射无法破坏该方法的单例
 *
 *
 */
public class Test {
    public static void main(String[] args) {

        EnumSingleton instance1 = null;
        EnumSingleton instance2 = EnumSingleton.getInstance();
        instance2.setData(new Object());

        try {
            FileOutputStream fos = new FileOutputStream("EnumSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(instance2);
            oos.flush();
            oos.close();

            FileInputStream fis = new FileInputStream("EnumSingleton.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            instance1 = (EnumSingleton)ois.readObject();
            ois.close();
            System.out.println(instance2.getData());
            System.out.println(instance1.getData());
            System.out.println(instance1.getData() == instance2.getData());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
