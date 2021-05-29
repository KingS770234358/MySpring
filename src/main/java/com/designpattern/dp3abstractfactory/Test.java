package com.designpattern.dp3abstractfactory;

/**
 * 抽象工厂模式
 * 优点：完整地描述了两个产品族：Java课程和Python课程，也
 * 描述了两个产品等级视频和笔记。
 * 缺点：继续扩展产品等级，将源码Source也加入课程，那么代码从抽象工厂到具体工厂要全部调整，不符合开闭原则。
 * （1）规定了所有可能被创建的产品集合，产品族中扩展新的产品困难，需要修改抽象工厂的接口。
 * （2）增加了系统的抽象性和理解难度。
 */
public class Test {
    public static void main(String[] args) {

        ICourseFactory factory = new JavaCourseFactory();
        IVideo video = factory.createVideo();
        INote note = factory.createNote();
        video.record();
        note.edit();

        factory = new PythonCourseFactory();
        video = factory.createVideo();
        note = factory.createNote();
        video.record();
        note.edit();

    }
}
