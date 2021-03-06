####概念：
单例模式（Singleton Pattern）是指确保一个类在任何情况下都绝对只有一个实例，并提供一个全局访问点。

####使用场景：
J2EE 标准中的ServletContext、ServletContextConfig等
Spring框架应用中的ApplicationContext、数据库的连接池等

####饿汉式单例模式 如Spring中ApplicationContext
饿汉式单例模式在类加载的时候就立即初始化，并且创建单例对象。
优点：没有加任何锁、执行效率比较高，用户体验比懒汉式单例模式更好；
它绝对线程安全，在线程还没出现以前就实例化了，不可能存在访问安全问题。
缺点：类加载的时候就初始化，不管用与不用都占着空间，浪费了内存。

####懒汉式单例模式
懒汉式单例模式的特点是：被外部类调用的时候内部类才会加载。

####线程模式调试 《Spring5核心原理与30个类手写实战》单例模式部分
1.加断点
2.右键断点，切换成线程模式 All -> Thread

步骤一：创建单例对象
if(lazy==null){
    lazy = new LazySimpleSingleton();
}

步骤二：接收单例对象
LazySimpleSingleton singleton = LazySimpleSingleton.getInstance();

两个线程同时进入步骤一的if语句块的情况下：
如果线程1先跑完了剩下的所有代码，再是线程2跑完剩下所有代码，则会产生两个不同的单例对象。
如果线程1先执行的步骤一，然后在步骤二执行之前等待；然后线程2执行步骤一，则线程1在接收单例对象的时候，自己创建的
单例对象已经被线程2的单例对象覆盖，导致线程1和线程2在步骤二接收到的单例对象都是相同的单例对象。

####synchronize修饰getInstance()方法保证线程安全
多线程调试模式下，如果线程1先进入synchronized修饰的方法，之后再切换到线程2运行，则线程2无法进入synchronized修饰的方法。
会进入MONITOR阻塞状态，IDEA建议Resume 线程1，恢复线程1，先让线程1释放锁。

####双检锁单例

####静态内部类单例

####反射破坏单例