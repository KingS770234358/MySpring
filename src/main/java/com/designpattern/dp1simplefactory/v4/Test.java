package com.designpattern.dp1simplefactory.v4;

/**
 * 优点：这里通过传入泛型上界 通过反射创界实例
 * 避免了过长限定名，加强控制
 * 简单工厂模式缺点：工厂类的职责相对过重，不易于扩展 [过于复杂的产品结构]
 * 随着产品链的丰富，如果每个课程的创建逻辑有区别，则工厂的职责会变得越来越多，有点像万能工厂，不便于维护
 */
public class Test {
    public static void main(String[] args) {

        CourseFactory.createCourse(JavaCourse.class).record();
        CourseFactory.createCourse(PythonCourse.class).record();

    }
}
