package com.designpattern.dp10adapterpattern.demo;

/**
 * 待修改的类
 */
public class AC220V {
    public int outputAC220V(){
        int output = 220;
        System.out.println("输出交流电" + output + "V");
        return output;
    }
}
