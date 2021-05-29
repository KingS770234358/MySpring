package com.designpattern.dp6proxypattern.staticproxy1;

public class Father implements Person{
    // 持有被代理类的引用
    private Kid kid;
    public Father(Kid kid){
        this.kid = kid;
    }
    @Override
    public void eat(){
        System.out.println("前增强...");
        kid.eat();;
        System.out.println("后增强...");
    }
}
