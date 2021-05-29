package com.designpattern.dp3abstractfactory;

/**
 * Python产品族的具体工厂PythonCourseFactory
 */
public class PythonCourseFactory implements ICourseFactory {
    @Override
    public INote createNote() {
        return new PythonNote();
    }

    @Override
    public IVideo createVideo() {
        return new PythonVideo();
    }
}
