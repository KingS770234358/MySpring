package com.designpattern.dp6proxypattern.dynamicproxy.jdkdynamicproxyPlus;

/**
 * 数据源路由对象
 * 动态切换数据源
 */
public class DynamicDataSourceEntry {
    // 默认数据源
    public final static String DEFAULT_SOURCE = null;
    private final static ThreadLocal<String> local = new ThreadLocal<String>();

    public DynamicDataSourceEntry(){}

    // 当前正在使用的数据源名字
    public static String get(){
        return local.get();
    }

    // 设置指定数据源
    public static void set(String source){
        local.set(source);
    }
    // 根据年份动态的设置数据源
    public static void set(int year){
        local.set("DB_" + year);
    }

    // 还原当前切换的数据源为默认数据源
    public static void reset(){
        local.set(DEFAULT_SOURCE);
    }

    // 清空数据源
    public static void clear(){
        local.remove();
    }

}
