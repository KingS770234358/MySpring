package com.RXJava.rxjavalib;

import com.RXJava.processorversion.TempInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.internal.operators.observable.ObservableError;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Collector;

public class TempObservable {

    /**
     * 合并 Observable    Observable.merge()
     * 合并多个城市的温度为数组,每个城市的温度可以经过下述的一些Observable的处理
     * 温度合并器
     *
     * Arrays.stream(town)                                town  :     Beijing       Landon      NewYork
     * TempObservable::getCelsiusTemperature                        Observable    Observable   Observable
     * .map(TempObservable::getCelsiusTemperature)                    temp1         temp2        temp3
     * .collect(Collectors.toList())                                 [temp1    ,    temp2    ,   temp3]
     * Observable.merge(以上所有步骤)                                     O  b  s  e  r  v  a  b  l  e (merge)
     */
    // public static Observable<TempInfo> getCelsiusTemperatures(String[] town){ // 字符串数组 和 变长参数 不一样！！！
    public static Observable<TempInfo> getCelsiusTemperatures(String... town){
        return Observable.merge(// merge方法迭代遍历每个Observable元素，并整合这些Observable的输出成1个列表， 对比concat
                                // 这些Observable就像1个整体的Observable一样
                Arrays.stream(town)// 边长字符串数组 转换成字符串流
                .map(TempObservable::getCelsiusTemperature) // 每个String被转换成以每秒1次频率发布温度数据的Observable对象
                .collect(Collectors.toList())// 第t秒，各个城市的温度数据数组
        );
    }

    /**
     * 过滤出负温度的Observable
     * 负温度过滤器
     * @param town
     * @return
     */
    public static Observable<TempInfo> getNegativeTemperature(String town){
        System.out.println("负温度过滤器");
        return getCelsiusTemperature(town)
                //
                .filter( temp-> {
                    System.out.println("筛选出负温度");
                    return temp.getTemp()<0; // Predicate 成立才会传给下游Observable/Observer处理
                });
    }

    /**
     * 转换温度的Observable
     * 温度转换器
     * @param town
     * @return
     */
    public static Observable<TempInfo> getCelsiusTemperature(String town){
        // System.out.println("Observable 流");
        System.out.println("摄氏度转换器");
        // 多个Observable组成数据处理流 类似Flow Api中的Processor，但比Processor灵活
        return getTemperature(town)
                //=============================================================================

                // 接受上游Observable对象，返回1个新的Observable对象，
                // 发送数据的频率与上游Observable对象一致，1s1个
                .map(temp->{
                    System.out.println("转换成摄氏度:" + town);
                    return new TempInfo(temp.getTown(), (temp.getTemp()-32) * 5 / 9);
                });
                //=============================================================================
    }

    /**
     * 源Observable
     * 温度发射器
     * @param town
     * @return
     */
    public static Observable<TempInfo> getTemperature(String town){
        // System.out.println("getTemperature方法");
        System.out.println("温度发射器");
        return Observable.create( // 接收1个 ObservableOnSubscribe 对象，创建Observable对象
            emitter //ObservableEmitter  // Consumer重写ObservableOnSubscribe.subscribe(ObservableEmitter)方法
                    -> Observable.interval(1, TimeUnit.SECONDS) // Observable创建1个无限递增的Long序列
                    .subscribe(
                            i->{
                            // System.out.println("+++" + emitter + "+++");
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
                                        // 源 数据发射器
                                        System.out.println("发射温度:" + town);
                                        emitter.onNext(TempInfo.fetch(town));
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
