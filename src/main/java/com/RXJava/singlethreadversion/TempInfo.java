package com.RXJava.singlethreadversion;

import java.util.Random;

/*****
 * 温度信息模型：
 *      城市
 *      温度
  通过工厂方法产生指定城市的TempInfo
 */

public class TempInfo {

    public static final Random random = new Random(); // 随机数生成器
    private final String town; // 城市
    private final int temp;

    public TempInfo(String town, int temp) {
        this.town = town;
        this.temp = temp;
    }
    public static TempInfo fetch(String town){ // 获取某个城市的温度信息，通过静态工厂方法创建TempInfo
        if(random.nextInt(10) == 0){// 每10次获取操作可能随机失败1次
            throw new RuntimeException("Error!");
        }
        return new TempInfo(town, random.nextInt(100)); // 返回华氏温度，0~99
    }

    @Override
    public String toString() {
        return "TempInfo{" +
                "town='" + town + '\'' +
                ", temp=" + temp +
                '}';
    }
    public int getTemp(){
        return this.temp;
    }
    public String getTown(){
        return this.town;
    }
}
