package com.designpattern.dp3abstractfactory;

/**
 * Java产品族的具体工厂JavaCourseFactory
 */
public class JavaCourseFactory implements ICourseFactory {
    @Override
    public INote createNote() {
        return new JavaNote();
    }

    @Override
    public IVideo createVideo() {
        return new JavaVideo();
    }
}
