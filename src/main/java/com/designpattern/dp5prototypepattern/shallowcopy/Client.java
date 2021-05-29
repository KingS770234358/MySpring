package com.designpattern.dp5prototypepattern.shallowcopy;

/**
 * 实际调用 浅拷贝方法 的客户端
 */
public class Client {
    private Prototype prototype;
    public Client(Prototype prototype){
        this.prototype = prototype;
    }
    public Prototype startClone(){
        // 这里返回的是抽象的Prototype接口
        return (Prototype)prototype.clone();
    }
}
