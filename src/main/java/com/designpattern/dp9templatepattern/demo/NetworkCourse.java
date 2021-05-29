package com.designpattern.dp9templatepattern.demo;

public abstract class NetworkCourse {

    // final保证方法不被重写 算法的整体骨架不变
    protected final void createCourse(){

        this.postPreResource(); // 分发预习资料..
        this.createPPT(); // 创建备课PPT...
        this.liveVideo(); // 在线直播...
        this.postNote();  // 提交笔记...
        this.postSouceCode(); // 提交源码...

        if(needHomework()){  // 钩子方法，微调业务流程
            checkHomework(); // 待子类具体实现的步骤
        }
    }

    // 待子类实现的抽象方法
    abstract void checkHomework();

    /*
     * 【钩子方法】：实现流程的微调
     * 钩子方法的主要目的是干预执行流程，使得控制行为更加灵活，更符合实际业务的需求。
     * 钩子方法的返回值一般为适合条件分支语句的返回值
     */
    protected boolean needHomework(){return false;}

    // final 算法的固定部分不能被重写
    final void postPreResource(){
        System.out.println("分发预习资料..");
    }
    final void createPPT(){
        System.out.println("创建备课PPT...");
    }
    final void liveVideo(){
        System.out.println("在线直播...");
    }
    final void postNote(){
        System.out.println("提交笔记...");
    }
    final void postSouceCode(){
        System.out.println("提交源码...");
    }
}
