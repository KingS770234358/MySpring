package com.designpattern.dp2factorymethod;


public class PythonCourseFactory implements ICourseFactory {
    @Override
    public ICourse create() {
        return new PYthonCourse();
    }
}
