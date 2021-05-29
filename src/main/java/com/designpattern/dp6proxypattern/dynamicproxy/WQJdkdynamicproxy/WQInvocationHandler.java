package com.designpattern.dp6proxypattern.dynamicproxy.WQJdkdynamicproxy;

import java.lang.reflect.Method;

public interface WQInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
