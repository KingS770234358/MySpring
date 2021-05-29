package com.designpattern.dp6proxypattern.staticproxy1;

public class Test {
    public static void main(String[] args) {
        Father f = new Father(new Kid());
        f.eat();
    }
}
