package com.designpattern.dp5prototypepattern.deepcopy;

import java.io.*;

// 继承原型类
// 实现Cloneable接口
public class ConcretePrototype extends Prototype implements Cloneable, Serializable {

    public Rectangle rectangle;

    public ConcretePrototype(){
        // 只是初始化
        this.rectangle = new Rectangle();
    }
    @Override
    protected Object clone() throws CloneNotSupportedException{
        return this.deepClone();
    }
    public Object deepClone(){

        try {
            // 整体过程 object -> oos -> bos -> ba -> bis -> ois -> readObject
            // 1.字节数组输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 2.对象输出流
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            // 3.把当前对象写入 对象输出流 最终写入到 字节数组输出流
            oos.writeObject(this);

            // 4.字节数组输入流           将 字节数组输出流 中的数据转成 字节数组 输入到 字节数组输入流中
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray()); // 字节数组输出流转字节数组
            // 5.字节数组输入流中的数据进入对象输入流
            ObjectInputStream ois = new ObjectInputStream(bis);
            // 6.从对象输入流中读取出对象
            ConcretePrototype copy = (ConcretePrototype)ois.readObject();
            return copy;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ConcretePrototype shallowCopy(ConcretePrototype target){
        ConcretePrototype copy = new ConcretePrototype();
        copy.name = target.name;
        copy.age = target.age;
        copy.rectangle = target.rectangle;
        return copy;
    }
}
