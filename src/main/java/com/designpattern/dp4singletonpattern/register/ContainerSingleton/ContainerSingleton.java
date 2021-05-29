package com.designpattern.dp4singletonpattern.register.ContainerSingleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 容器式单例模式适用于不同的单例实例非常多的情况，便于管理。但它是非线程安全的。
 */
public class ContainerSingleton {
    private ContainerSingleton(){}
    // 用来存放所有不同单例实例的Map
    private static Map<String, Object> ioc = new ConcurrentHashMap<String,Object>();
    public static Object getBean(String className){
        synchronized (ioc){
            if(!ioc.containsKey(className)){
                Object obj = null;
                try{
                    obj = Class.forName(className).getDeclaredConstructor().newInstance();
                    ioc.put(className, obj);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return obj;
            }else{
                return ioc.get(className);
            }
        }
    }
}
