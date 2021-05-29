package com.designpattern.dp1simplefactory.v2;

/**
 * 优点：由课程工厂创建课程，把具体对象的创建细节隐藏起来
 * 缺点：如果继续扩展，要增加前端课程，那么工厂CourseFactory中的create（）方法就要
 * 每次都根据产品的增加修改代码逻辑，不符合开闭原则。
 */
public class Test {
    public static void main(String[] args) {

        CourseFactory.createCourse("java").record();
        CourseFactory.createCourse("python").record();

    }
}
