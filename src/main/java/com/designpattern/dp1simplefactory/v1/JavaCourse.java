package com.designpattern.dp1simplefactory.v1;

public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("录制Java课程");
    }
}
