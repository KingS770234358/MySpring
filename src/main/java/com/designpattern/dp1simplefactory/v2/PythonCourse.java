package com.designpattern.dp1simplefactory.v2;

public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("录制Python课程");
    }
}
