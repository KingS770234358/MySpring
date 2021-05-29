package com.designpattern.dp12observerpattern;

import java.util.Observable;

/**
 * 继承“被观察”类
 */

public class Student extends Observable {

    private String name = "学生生态圈";
    // 单例模式
    private static Student stu = null;

    public static Student getInstance(){
        if(stu==null){
            stu = new Student();
        }
        return stu;
    }

    public String getName(){
        return this.name;
    }

    public void publishQuestion(Question q){
        System.out.println(q.getUserName() + "在" + this.name + "上提交了一个问题");
        setChanged();
        notifyObservers(q);
    }

}
