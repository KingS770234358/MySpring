package com.designpattern.dp1simplefactory.v3;

public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("v3录制Python课程");
    }
}
