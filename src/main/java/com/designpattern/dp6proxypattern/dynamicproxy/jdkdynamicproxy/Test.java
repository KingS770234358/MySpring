package com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxy;

/**
 * JDK动态代理采用【字节重组】，重新生成对象来替代原始对象，以达到动态代理的目的。
 * JDK动态代理生成对象的步骤如下：
 * （1）获取被代理对象的引用，并且反射获取它的所有接口，。
 * （2）JDK动态代理类重新生成一个新的类，同时新的类要实现被代理类实现的所有接口。
 * （3）动态生成Java代码，新加的业务逻辑方法由一定的逻辑代码调用（在代码中体现）。
 * （4）编译新生成的Java代码.class文件。
 * （5）重新加载到JVM中运行。
 * 该类.class在静态块中用反射查找到了目标对象的所有方法，
 * 而且保存了所有方法的引用，重写的方法用反射调用目标对象的方法。
 */
public class Test {
    public static void main(String[] args) {
        try {
            Person obj = (Person)new JDKMedia().getProxyInstance(new Kid()); // 传入被代理对象作为参数
            obj.eat(); // 代理对象的eat方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
