package com.RXJava.twothreadversion;

import java.util.concurrent.Flow;

//                                     Subscriber 和 Subscription之间传输的 TempInfo
public class TempSubscriber implements Flow.Subscriber<TempInfo> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) { // 在订阅的时候，Publisher调用该方法
        this.subscription = subscription; // 回传Subscription对象给Subscriber
        // 订阅的时候就执行背压，限制Subscription/Publisher向Subscriber发送次数
        // 执行1次 subscription.request() 方法，是指 向subscription发送第1个请求，
        // subscription.request(1)中的方法参数1是指 ：
        // subscription.request中的for循环只执行1次，即只执行1次subscriber.onNext(T)方法 —— 向Subscriber发送1次TempInfo
        subscription.request(1);
    }

    @Override
    public void onNext(TempInfo tempInfo) {
        System.out.println("Subscriber线程：" + Thread.currentThread().getName());
        System.out.println( tempInfo);  // 打印输出接收到的温度 —— 实际的1次业务逻辑
        // 执行1次 subscription.request() 方法， 向subscription发送1个请求。
        // 参数中的1是指数据的份数，subscription.request中的for循环执行subscriber.onNext(T)的次数
        subscription.request( 1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Done!");
    }
}
