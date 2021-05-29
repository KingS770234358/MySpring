package com.designpattern.dp12observerpattern;

/**
 * 观察者模式
 * 定义了对象之间的一对多依赖，让多个观察者对象同时监听一个主体对象。
 * 当主体对象发生变化时，它的所有依赖者（观察者）都会收到通知并更新，属于行为型模式。
 * 观察者模式也叫作发布订阅模式，主要用于在 关联行为之间 建立一套 触发机制 的场景。
 *
 * 示例：学生发布问题通知老师
 *
 * 优点：
 * （1）在观察者和被观察者之间建立了一个抽象的耦合。
 * （2）观察者模式支持广播通信。
 * 缺点：
 * （1）观察者之间有过多的细节依赖、时间消耗多，程序的复杂性更高。
 * （2）使用不当会出现循环调用。
 *
 */
public class Test {
    public static void main(String[] args) {
        Student student = Student.getInstance();
        Teacher t1 = new Teacher("Tom");
        Teacher t2 = new Teacher("Mic");

        student.addObserver(t1);
        student.addObserver(t2);

        // 业务逻辑代码
        Question q = new Question();
        q.setUserName("小明");
        q.setContent("观察者模式适用于哪些场景？");

        student.publishQuestion(q);
    }
}
