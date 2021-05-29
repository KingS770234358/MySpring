package com.designpattern.dp1simplefactory.v4;

public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("v4录制Python课程");
    }
}
