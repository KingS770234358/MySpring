package com.RXJava.rxjavalib;

import com.RXJava.processorversion.TempInfo;
import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class TempObservable {
    public static Observable<TempInfo> getTemperature(String town){
        System.out.println("getTemperature方法");
        return Observable.create( // 接收1个 ObservableOnSubscribe 对象，创建Observable对象
            emitter //ObservableEmitter  // Consumer重写ObservableOnSubscribe.subscribe(ObservableEmitter)方法
                    -> Observable.interval(1, TimeUnit.SECONDS) // Observable创建1个无限递增的Long序列
                    .subscribe(
                            i->{
                            System.out.println("+++" + emitter + "+++");
                            /*
                            * Observer对象被回收 —— 由于前置操作失败/完成，Observer对象被回收
                            *          即调用了emitter.onError(e)/emitter.onComplete()方法
                            * */
                            // emitter.isDisposed()即判断emitter持有的Observer对象是否被回收
                            if(!emitter.isDisposed()){// 只有当emitter持有的Observer对象未被回收时才执行操作
                                // 已经产生了5个数字，即产生了5次温度，则发出完成信号，终止Observer对象，关闭相应的流
                                if(i>=5){
                                    emitter.onComplete();
                                }else{
                                    try {
                                        emitter.onNext(TempInfo.fetch("New York"));
                                    }catch (Exception e){
                                        emitter.onError(e);
                                    }
                                }
                            }
                        }
                    )
            );
    }
}
