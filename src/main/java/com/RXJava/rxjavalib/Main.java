package com.RXJava.rxjavalib;

import com.RXJava.processorversion.TempInfo;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class Main {
    public static void main(String[] args) {

        /***
         * 仅使用一个接收事件的Consumer 【i -> System.out.println(TempInfo.fetch("New York"))】
         * 实现 【onNext方法的】  Observer对象  订阅1个Observable；
         * 其他onSubscriber、onError、onComplete方法都是用默认值,错误onError处理逻辑为空
         */
        // SimpleUse.simpleTest();
        // 下面实现功能更为丰富的Observer 订阅Observable
        // TempObservable.getTemperature("New York").blockingSubscribe(new TempObserver());
        // TempObservable.getCelsiusTemperature("New York").blockingSubscribe(new TempObserver());
        // TempObservable.getNegativeTemperature("New York").blockingSubscribe(new TempObserver());
        System.out.println("=======================================================================");
        // merge Observable，合并处理 多个城市 每1秒的温度数据    【边长参数的传递】
        TempObservable.getCelsiusTemperatures("beijing","changsha","shanghai")
                .blockingSubscribe(new TempObserver());
        // merge Observable输出结果（有待进一步理解，结果乱序，有时直接报异常-而不是按照代码写的输出Error！）
        /*
        *
            摄氏度转换器                   "beijing"的Observable流
            温度发射器

            摄氏度转换器                   "changsha"的Observable流
            温度发射器

            摄氏度转换器                   "shanghai"的Observable流
            温度发射器

            发射温度                                beijing 的最上游Observable
            发射温度                                changsha的最上游Observable
            发射温度                                shanghai的最上游Observable

            转换成摄氏度                             beijing 的第二个Observable
            转换成摄氏度                             changsha的第二个Observable
                                                   shanghai的第二个Observable 执行出错
            TempInfo{town='shanghai', temp=-1}
            TempInfo{town='beijing ', temp=1}
            Error!
        */

    }
}
