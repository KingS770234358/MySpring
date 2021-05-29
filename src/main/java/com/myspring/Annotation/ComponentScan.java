package com.myspring.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring提供的 配置类扫描路径的注解
 */

/*
 * 修饰注解存在的阶段：
 * 1. .SOURCE注解只存在源码中
 * 2. .CLASS存在于字节码中，但运行时无法获得
 * 3. .RUNTIME存在于字节码文件中，运行时可以通过反射获得
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 只能加在类上面
public @interface ComponentScan {
    String value(); // 返回扫描路径的方法属性
}
