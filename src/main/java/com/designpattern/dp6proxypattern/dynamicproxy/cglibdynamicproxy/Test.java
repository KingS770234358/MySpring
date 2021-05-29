package com.designpattern.dp6proxypattern.dynamicproxy.cglibdynamicproxy;

/**
 * Cglib动态代理：
 * 代理类会获得所有从父类(被代理类)继承来的方法
 * 并且会有 MethodProxy 与之对应，比如 Method CGLIB$findLove$0$Method、MethodProxy CGLIB$findLove$0$Proxy
 * 这些方法在 代理类 的findLove（）方法中都有调用。
 *
 * 调用过程为：
 * 代理对象调用 this.findLove（）方法 →调用拦截器 → methodProxy.invokeSuper
 * → CGLIB$findLove$0 → 被代理对象findLove（）方法。
 *
 * FastClass提速：
 * CGLib代理执行代理方法的效率之所以比JDK的高，是因为CGlib采用了FastClass机制，
 * 为 代理类 和 被代理类 各生成一个类，这个类会为 代理类 或 被代理类 的方法分配一个index（int类型）；
 * 这个index当作一个入参，FastClass就可以直接定位要调用的方法并直接进行调用，
 * 省去了反射调用，所以调用效率比JDK代理通过反射调用高
 * FastClass并不是跟代理类一起生成的，而是在第一次执行MethodProxy的invoke（）或invokeSuper（）方法时生成的，
 * 并放在了缓存中。
 *
 *
 */
public class Test {
    public static void main(String[] args){

        try {
            Kid obj = (Kid)new Father().getProxyInstance(Kid.class);
            obj.eat();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
