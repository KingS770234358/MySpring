### 反应式编程 —— 新的计算模式
1.背景 重要性 意义
大数据PB级别、异构环境（应用部署到不同的设备上）、使用模式（ms级响应、随时在线）

2.作用
以 异步的方式 处理、整合来自不同系统和源头的数据流，在处理数据的同时进行反馈，
让数据对用户的响应更及时；
协调多个组件->反应式系统；

3.特性及优点——《反应式宣言》
·响应性-快速响应，可预期的响应时间
·韧性  -失败时仍继续响应服务；一系列的advice，从时间空间两个维度对组件进行解耦，
       每个组件都能异步的方式向其他组件分发任务
·弹性  -工作负载，组件要有能力自动地适配和服务更大的负荷；
·消息驱动 -跨组件通信通过 异步消息 传递，
 韧性（以消息传递组件失败） 弹性（通过监控交换消息规模的变化），优化资源分配；

为 CPU密集型 和 IO密集型的操作分别创建单独的线程池

4.Flow类 
  java.util.concurrent.Flow包含4个嵌套的接口 体现反应式项目定义的标注“发布-订阅”模型
  
  ·发布者（Publisher）
  @FunctionalInterface
  public interface Publisher<T>{
    void subscribe(Subscribe<? super T> s); 
  }
  
  ·订阅者（Subscriber）
  public interface Subscriber<T>{
    void onSubscribe(Subscription s);
    void onNext(T t);
    void onError(Throwable t);
    void onComplete();
  }
  -事件的发布以及对应方法的调用遵守顺序: onSubscribe onNext* (onError|onComplete)?
  -Subscriber向Publisher注册时，Publisher的第一个动作就是调用Subscriber对象的onSubscribe方法，
   给Subscriber对象回传一个Subscription对象；Subscriber通过Subscription对象的第一个方法告诉Publisher
   自己已经准备好接收多少个事件（限流），通过Subscription对象的第二个方法取消Subscription，告诉Publisher不再
   接收来自Publisher的事件
  
  ·订阅（Subscription——管理发布者和订阅者之间的关系）
  public interface Subscription{
    void request(long n);
    void cancel();
  }
  
  ·处理者（Processor）继承 发布者 和 订阅者， 没有添加额外方法
  public interface Processor<T, R> extends Subscriber<T>, Publisher<R>{
    ....
  }
  
  
  凭借Flow类相互关联的接口或者静态方法可以构造流控组件
  -背压机制：生产事件的上游根据消费事件的下游的反馈控制事件的产生
  
5.Demo 温度汇报程序
 · TempInfo 远程温度计，持续不断的汇报温度（随机生成）
 · TempSubscriber 监听报告事件，并打印输出某个城市的温度监控器返回的温度Stream

对于Subscriber对象而言
- subscriber.onSubscribe方法：向Publisher订阅，初始化subscriber的subscription对象；
  调用Subscription.request(n)方法，向Subscription对象发送第1次请求，请求n份待处理的数据；
- Subscriber.onNext(T) 被 Subscription.request方法调用
  是接收来自Subscription的数据，实际进行1次业务逻辑处理，处理数据T；
  同时可以再调用1次Subscription.request(n)方法向Subscription发送1次请求，请求n份待处理的数据
- Subscriber.onError(Throwable t) 被 Subscription.request方法 调用
 （Subscription.request方法在for循环执行Subscriber.onNext(T)向Subscriber发送数据时出错，就调用
  Subscriber.onError(Throwable t)方法做相应处理）
- Subscriber.onComplete() 被 Subscription.cancel方法 调用，取消订阅

对于Subscription对象而言
- Subscription.request(n) 被 Subscriber.onSubscriber方法或onNext方法调用，
  是接收来自Subscriber对象发来的1次请求，
  其中又：
      for循环调用n次Subscriber.onNext(T)方法，向Subscriber发送n次数据，Subscriber进行n次业务逻辑，处理n次数据T
      或者
      调用1次Subscriber.onError方法，表示在 Subscription发送数据给Subscriber 或者 Subscriber处理数据过程中出错，
      此时可以选择终止for循环  
- subscription.cancel() 调用 subscriber.onComplete()方法，该方法中不会再调用subscription.request(n)发起请求，业务终止，

对于Publisher对象而言
- 主函数Publisher.subscribe(Subscriber)方法，Subscriber向Publisher订阅事件，
  该Subscriber获得1个 包含自身 的Subscription对象

TIPS:
  onNext 和 request 之间存在递归， 被Subscriber.onComplete或者Subscriber.onError终结;
  Subscription可以视为 数据的提供方
  
6.Processor
  Processor 先是1个Subscriber，再是1个Publisher。
  它要先以Subscriber的身份注册到某个Publisher中，
  接收处理完该Publisher发送的数据之后，再以Publisher的身份把处理完的数据再发送出去
  数据大致流动方向：Publisher       ->              TownProcessor       ->  ...  ->     TempProcessor          ->        Subscriber
                                         Subscriber  |    Publisher            Subscriber  |    Publisher        
                                                     <---------------------------------------------------------Subscription.request()
                                                   onNext-------------------------------------------------------------->

7.RxJava反应式库
  ·RxJava提供了两个版本的Flow.Publisher的实现：io.reactivex.Flowable类 和 io.reactivex.Observable类
  ·io.reactivex.Flowable类：提供Java9 Flow中基于拉模式的背压特性，防止Subscriber被Publisher快速生成的大量数据压垮。
   Subscriber可以通过request(Long.MAX_VALUE)的方式关闭背压功能（不建议）
  ·io.reactivex.Observable类 不支持背压，适用于 不适合进行背压的场景，如用户接口事件（移动鼠标等），不适合通过背压进行反馈，对
   事件的生产者进行背压，让ta慢点移动鼠标或者停止移动鼠标，以减缓或停止上游事件的生成。
   当流元素个数不超过1k，或者正在处理基于图形用户界面的事件流，建议使用非背压版本的Observable作为Publisher。
 7.1 Observable的使用方式
   ① Observable<String> strings = Observable.just("first", "second");
   just()工厂方法可以将1个或多个元素转换为Observable，在适当的时候释放对应元素；
   Subscriber会依次接受度奥onNext("first") onNext("second") 以及 onComplete()消息
   ② Observable工厂方法（尤其是当应用需要与用户执行实时交互是，会按照固定的时间间隔发出事件）
   Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECOND);
   返回1个Observable，以指定的时间间隔发送1个由long类型组成的无限递增序列，由0开始计数。
   可以使用onePerSec作为另1个Observable的基础，每个1秒反馈1次指定城市的温度报告
 7.2 Observer接口
 public interface Observer<T>{
    void onSubscribe(Disposable d);
    void onNext(T t);
    void onError(Throwable t);
    void onComplete();
 }
 7.3 快速订阅Observable:
 仅使用一个接收事件的Consumer 【i -> System.out.println(TempInfo.fetch("New York"))】
 实现 【onNext方法的】 Observer对象订阅1个Observable；
 其他onSubscriber、onError、onComplete方法都是用默认值
 onePerSec.subscribe( i -> System.out.println(TempInfo.fetch("New York")))
 
 4.守护线程
 执行   Observable每隔指定时间间隔就发送1次数据  的线程是RxJava计算线程池中的守护线程
 直接把3中的语句放在main方法中执行，main程序执行完毕就立刻退出了，导致守护线程还没有产生任何输出就被终止了。
 解决方案：
 4.1 执行完3中代码后，让main线程进入sleep；
 4.2 使用blockingSubscribe方法调用当前线程（main函数所在线程）的回调函数。
 onPerSec.blockingSubscribe( i -> System.out.println(TempInfo.fetch("New York")));
 
 5.ObservableEmitter 相当于不带onSubscribe方法的Observer
 public interface ObservableEmitter{
    void onNext(T t);
    void onError(Throwable t);
    void onComplete();
 }
 定制功能更为丰富的 订阅者 ObservableEmitter
 getTemperature===================================================================================> Observable
 public static Observable<TempInfo> getTemperature(String town){                                        |
     // Observable.create(ObservableOnSubscribe)                                                        |
     // ObservableOnSubscribe中的subscribe(ObservableEmitter)方法通过传入1个ObservableEmitter创建1个        |
     // Observable对象，进行订阅。                                                            Observer持有ObservableEmitter的引用
     // ObservableEmitter 相当于不带onSubscribe方法的Observer                                              | 
     return Observable.create(                                                                          |
            // ======================== 以下是ObservableOnSubscribe的实现 ==========================> ObservableEmitter
            // 传入1个 ObservableEmitter                                                                 |
                emitter -> Observable.interval(1, TimeUnit.SECONDS).subscribe(                          |
                           i->{                                                                         |
                                if(!emitter.isDisposed()){                                              |
                                   if(i>=5){                                                            |
                                       emitter.onComplete();                                            |
                                   }else{                                                               |
                                       try {                                                            |
                                           emitter.onNext(TempInfo.fetch("New York"));    ObservableEmitter持有Observer的引用
                                       }catch (Exception e){                                            |
                                           emitter.onError(e);                                          |
                                       }                                                                |
                                   }                                                                    |
                                }                                                                       |
                           }                                                                            |
                   )                                                                                    |
            // ======================== 以上是ObservableOnSubscribe的实现 ==========================      |  
           );                                                                                           |
 }                                                                                                      |
 getTemperature("New York").blockingSubscribe(new TempObserver());=================================> Observer