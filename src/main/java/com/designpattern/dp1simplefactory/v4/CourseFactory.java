package com.designpattern.dp1simplefactory.v4;

import com.designpattern.dp1simplefactory.v4.ICourse;

public class CourseFactory {
    /**
     * 优点：这里通过传入泛型上界 通过反射创界实例
     * 避免了过长限定名，加强控制
     * @param clazz
     * @return
     */
    public static ICourse createCourse(Class<? extends ICourse> clazz){
        if(null!=clazz){
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
