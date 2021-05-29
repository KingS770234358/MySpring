package com.myspring;

/**
 * spring提供的初始化对象
 */
public interface InitializingBean {
    void afterPropertySet() throws Exception;
}
