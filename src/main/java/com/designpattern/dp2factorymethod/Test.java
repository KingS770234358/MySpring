package com.designpattern.dp2factorymethod;

/**
 * 工厂方法模式:根据单一职责原则将职能继续拆分，专人干专事。
 * Java课程由Java工厂创建，Python课程由Python工厂创建，对工厂本身也做一个抽象。（生产工厂的工厂）
 *
 * 适用于以下场景：
 * （1）创建对象需要大量重复的代码。
 * （2）客户端（应用层）不依赖于产品类实例如何被创建、如何被实现等细节。
 * （3）一个类通过其子类来指定创建哪个对象。
 *
 * 缺点：
 * （1）类的个数容易过多，增加复杂度。
 * （2）增加了系统的抽象性和理解难度。
 */
public class Test {
    public static void main(String[] args) {

        ICourseFactory factory = new JavaCourseFactory();
        ICourse course = factory.create();
        course.record();

        factory = new PythonCourseFactory();
        course = factory.create();
        course.record();

    }
}
