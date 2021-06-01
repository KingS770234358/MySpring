package com.RXJava.rxjavalib;

import com.RXJava.processorversion.TempInfo;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class SimpleUse {
    public static void simpleTest(){
        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);
        // 发布事件的线程是RxJava计算线程池中的守护线程，main线程执行完毕即被终止
        // onePerSec.Subscribe( i-> System.out.println(TempInfo.fetch("New York!")));
        /*
        *   仅使用一个接收事件的Consumer 【i -> System.out.println(TempInfo.fetch("New York"))】
            实现 【onNext方法的】  Observer对象  订阅1个Observable；
            其他onSubscriber、onError、onComplete方法都是用默认值,错误onError处理逻辑为空
        * */
        /*
         *   这里的i 是onePerSec<Long> 每秒生成1个的递增序列
         * */
        onePerSec.blockingSubscribe( i-> {
            System.out.print("i=" + i + " ");
            System.out.println(TempInfo.fetch("New York!"));
        });
        // ****************** 上述的Observable接收1个简单的Observer（只实现了onNext方法）来处理数据************
    }
}
