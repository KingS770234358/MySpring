package com.designpattern.dp1simplefactory.v1;

/**
 * 应用层代码需要依赖JavaCourse。
 * 如果业务扩展，继续增加PythonCourse甚至
 * 更多课程，那么客户端的依赖会变得越来越臃肿（要创建的Course对象会越来越多）
 */
public class Test {
    public static void main(String[] args) {
        ICourse course = new JavaCourse();
        course.record();
    }
}
