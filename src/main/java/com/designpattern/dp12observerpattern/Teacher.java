package com.designpattern.dp12observerpattern;

import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 * 实现观察者接口
 */
public class Teacher implements Observer {
    private String  name;
    public Teacher(String name){
        this.name = name;
    }
    @Override
    public void update(Observable observable, Object o) {
        Student stu = (Student) observable;
        Question q = (Question) o;
        System.out.println("=======================");
        System.out.println(name + "老师，您好！\n"+
                "您收到了来自自“" + stu.getName() + "”的提问，希望您解答，问题内容如下：\n"+
                "提问者：" + q.getUserName());
    }
}
