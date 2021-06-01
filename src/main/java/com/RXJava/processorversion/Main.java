package com.RXJava.processorversion;

import java.util.concurrent.Flow;

/**
 * publisher.subscribe(Subscriber)
 * -> Subscriber.onSubscribe(Subscription(Subscribe, ...))
 * 递归开始...
 * -> Subscription.request()
 * -> Subscription.TownProcessor.onNext()
 * -> Subscription.TempProcessor.onNext()
 * -> Subscription.Subscriber.onNext()
 * -> Subscriber.Subscription.request()
 * 递归结束
 * -> Subscription.cancel()
 * -> Subscription.TownProcessor.onComplete()
 * -> Subscription.TempProcessor.onComplete()
 * -> Subscription.Subscriber.onComplete()
 * 或者
 * -> Subscription.request()
 * -> Subscription.TownProcessor.onError()
 * -> Subscription.TempProcessor.onError()
 * -> Subscription.Subscriber.onError()
 */

public class Main {
    public static void main(String[] args) {

        //                                              最下游的订阅者
        getCelTempPublisher("Harbin").subscribe(new TempSubscriber());
    }
    public static Flow.Publisher<TempInfo> getCelTempPublisher(String town){ // 获取发布指定城镇天气的TempPublisher

        return subscriber -> {
                    TempProcessor tempProcessor = new TempProcessor();
                    tempProcessor.subscribe(subscriber); // TempProcessor - Subscriber
                    TownProcessor townProcessor = new TownProcessor();
                    townProcessor.subscribe(tempProcessor);
                    System.out.println("主线程：" + Thread.currentThread().getName());
                    //     最上游的Publisher 委托给TempSubscription发布事件，指定其下游订阅者是tempProcessor
                    townProcessor.onSubscribe(new TempSubscription(townProcessor, town)); // Publisher - TempProcessor
/**
 * Publisher Processor Subscriber
 *         Subscription
 * 1.Processor.subscribe(Subscriber)最下游Subscriber自下而上向Processor先发起订阅，
 *   使得上游Processor持有下游Subscriber
 * 2.Processor.onSubscribe(Subscription(Processor))
 *   Subscription：最上游的Publisher创建【包含最上游Processor的】Subscription对象
 *   上游的Processor.onSubscribe，递归下游的Processor/Subscriber.onSubscribe(Subscription)方法，
 *   最后调用最下游Subscribe.onSubscribe(Subscription)方法，将Subscription对象传递给最下游的Subscriber持有
 * 3.最下游的Subscribe.onSubscribe(Subscription)方法时向Publisher(本质是向Subscription)发起request，
 *   调用Subscription.request()方法；该方法中调用的是Publisher/Processor的直接下游Processor/Subscriber.onNext(T)方法，
 *   即最下游Subscriber对象持有的Subscription.request()方法中的onNext(T)方法是从最上游的Processor.onNext()方法开始执行
 *
 */
        };
    }

}
