package com.designpattern.dp1simplefactory.v3;

import java.lang.reflect.InvocationTargetException;

/**
 * 优点：课程工厂类通过反射机制，根据传入的 课程类全限定名 创建对象
 * 避免业务拓展（新增课程）对代码的修改。
 * */
public class CourseFactory {
    /**
     * 缺点：方法参数是字符串，可控性有待提升
     */
    public static ICourse createCourse(String courseClassName){
        if(null!=courseClassName && !"".equals(courseClassName)){
            try {
                return (ICourse) Class.forName(courseClassName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
