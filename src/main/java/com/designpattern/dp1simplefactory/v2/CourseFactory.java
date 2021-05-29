package com.designpattern.dp1simplefactory.v2;

/**
 * 课程工厂类根据传入的课程名称创建不同的课程对象
 * */
public class CourseFactory {
    public static ICourse createCourse(String courseName){
        if("java".equals(courseName)){
            return new JavaCourse();
        }else if("python".equals(courseName)){
            return new PythonCourse();
        }
        return null;
    }
}
