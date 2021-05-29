package com.designpattern.dp9templatepattern.demo;

public class BigDataCourse extends NetworkCourse {

    private boolean needHomeworkFlag = false;

    public BigDataCourse(boolean needHomeworkFlag){
        this.needHomeworkFlag = needHomeworkFlag; // 修改课程默认的配置
    }

    @Override
    void checkHomework() {
        System.out.println("检查大数据的课后作业...");
    }
    @Override
    protected boolean needHomework(){
        return this.needHomeworkFlag;
    }
}
