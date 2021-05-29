package com.designpattern.dp5prototypepattern.deepcopy;

/**
 * 防止克隆破坏单例模式
 * 禁止深克隆：
 * 1.单例类不实现 Cloneable接口，
 * 2.重写clone（）方法，在clone（）方法中返回单例对象即可，
 */
public class Test {
    public static void main(String[] args) {

        ConcretePrototype cp = new ConcretePrototype(); // 待拷贝类
        // 深拷贝
        try {
            ConcretePrototype cloneResult = (ConcretePrototype) cp.clone();
            System.out.println("深拷贝：" + (cp.rectangle == cloneResult.rectangle)); // false 克隆出不同的对象
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        // 浅拷贝
        ConcretePrototype q = new ConcretePrototype();
        ConcretePrototype shallowCloneResult = q.shallowCopy(q);
        System.out.println("浅拷贝：" + (q.rectangle == shallowCloneResult.rectangle)); // true 引用的对象Rectangle不变
    }
}
