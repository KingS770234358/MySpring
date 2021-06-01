package com.RXJava.twothreadversion;

import java.util.concurrent.Flow;

/**
 * publisher.subscribe(Subscriber)
 * -> Subscriber.onSubscribe(Subscription(Subscribe, ...))
 * 递归开始...
 * -> Subscription.request()
 * -> Subscription.Subscriber.onNext()
 * -> Subscriber.Subscription.request()
 * -> Subscription.Subscriber.onNext()
 * -> Subscriber.Subscription.request()
 * ...
 * 递归结束
 * -> Subscription.cancel() -> Subscription.Subscriber.onComplete()
 * 或者
 * -> Subscription.request() -> Subscription.Subscriber.onError()
 */
public class Main {
    public static void main(String[] args) {
        getTempPublisher("Harbin").subscribe(new TempSubscriber());
    }
    public static Flow.Publisher<TempInfo> getTempPublisher(String town){ // 获取发布指定城镇天气的TempPublisher
        // 返回值就是实现Publisher.subscribe(Subscriber)方法
        // Publisher.subscribe(Subscriber)调用subscriber.onSubscribe(Subscription)方法，
        // 回传1个Subscription给Subscriber，触发了流式处理
        return subscriber -> {
                    System.out.println("主线程：" + Thread.currentThread().getName());
                    subscriber.onSubscribe(new TempSubscription(subscriber, town));
               };
    }
}
