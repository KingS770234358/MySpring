package com.designpattern.dp1simplefactory.v3;

public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("v3录制Java课程");
    }
}
