package com.designpattern.dp1simplefactory.v2;

public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("录制Java课程");
    }
}
