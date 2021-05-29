package com.designpattern.dp1simplefactory.v3;

/**
 * 优点：课程工厂是通过 反射 的方式根据传入的 课程类全限定名 创建课程类的实例
 * 避免业务拓展（新增课程）对代码的修改。
 * 缺点：方法参数是字符串，可控性有待提升
 */
public class Test {
    public static void main(String[] args) {
        // 这里传入课程类全限定名
        CourseFactory.createCourse("com.designpattern.dp1simplefactory.v3.JavaCourse").record();
        CourseFactory.createCourse("com.designpattern.dp1simplefactory.v3.PythonCourse").record();

    }
}
