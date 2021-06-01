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
        TempObservable.getTemperature("New York").blockingSubscribe(new TempObserver());

    }
}
