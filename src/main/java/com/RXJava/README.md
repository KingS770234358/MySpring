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
  
  