package com.designpattern.dp10adapterpattern.demo;

/**
 * 适配器类整体上保留了“待修改类”AC220V的大部分属性
 * 只是对部分属性作出修改（适配当前的使用场景）
 */
public class PowerAdapter implements DC5V {

    private AC220V ac220V;
    public PowerAdapter(AC220V ac220V){
        this.ac220V = ac220V;
    }
    @Override
    public int outputDC5V() {
        int adapterInput = ac220V.outputAC220V();
        // 适配器（变压器）
        int adapterOutput = adapterInput/44;
        System.out.println("PowerAdapter以" + adapterInput + "V电压作为输入，降压后输出" + adapterOutput + "V");
        return adapterOutput;
    }
}
