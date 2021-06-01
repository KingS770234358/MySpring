package com.RXJava.processorversion;

import java.util.concurrent.Flow;

public class TownProcessor implements Flow.Processor<TempInfo, TempInfo> {
    // 作为发布者的subscriber属性
    private Flow.Subscriber<? super TempInfo> subscriber;
    // 以下是作为订阅者的subscription属性
    private Flow.Subscription subscription;

    // 作为发布者的subscribe方法
    @Override
    public void subscribe(Flow.Subscriber<? super TempInfo> subscriber) {
        // subscriber.onSubscribe(new TempSubscription(subscriber, this.town));
        this.subscriber = subscriber;
    }
    // 以下是作为订阅者的方法
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(TempInfo tempInfo) {
        // 处理上游发布者发布的数据，转化后再交由下游的订阅者处理
        System.out.println("中游的TownProcessor进行城镇名的转换...");
        this.subscriber.onNext(new TempInfo("****" + tempInfo.getTown() + "****", tempInfo.getTemp()));
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("中游的TownProcessor出错！" + throwable.getMessage());
        this.subscriber.onError(throwable);
    }

    @Override
    public void onComplete() {
        System.out.println("中游的TownProcessor完成！");
        // this.subscriber.getSubscription().cancel();
        this.subscriber.onComplete();
    }
}
