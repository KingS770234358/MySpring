package com.RXJava.singlethreadversion;

import java.util.concurrent.Flow;

/**
 * Subscription接口的实现 “某个城市温度的Subscription对象”
 * ①request()的try     向Subscriber发送指定城市的温度
 * ②request()的catch   向Subscriber发送异常
 * ③cancel()           向Subscriber发送 onComplete 信号
 * 在Subscriber请求温度报告时返回对应的数据
 */
public class TempSubscription implements Flow.Subscription {

    private final Flow.Subscriber<? super TempInfo> subscriber;
    private final String town;

    public TempSubscription(Flow.Subscriber<? super TempInfo> subscriber, String town){
        this.subscriber = subscriber; // 持有subscriber的引用
        this.town = town;
    }

    @Override
    public void request(long l) {
        for (long i = 0L; i < l; i++){
            try {
                // 产生温度信息TempInfo ，调用subscriber的onNext方法处理
                subscriber.onNext(TempInfo.fetch( town )); // 向Subscriber发送指定城市的温度
            } catch (Exception e) {
                subscriber.onError( e ); // 查询温度时发生异常，向Subscriber发送异常
                break; // 中断
            }
        }
    }

    @Override
    public void cancel() { // 如果调用subscription.cancel()，向Subscriber发送 onComplete 信号
        subscriber.onComplete();
    }
}
