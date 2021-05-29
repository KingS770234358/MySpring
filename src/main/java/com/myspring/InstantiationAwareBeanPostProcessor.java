package com.myspring;

import java.util.Properties;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object postProcessBeforInstantiation(Class<?> beanClass, String beanName) throws Exception;

    Object postProcessAfterInstantiation(Object bean, String beanName) throws Exception;

    Object postProcessProperties(String pvs, Object bean, String beanName) throws Exception;
}
