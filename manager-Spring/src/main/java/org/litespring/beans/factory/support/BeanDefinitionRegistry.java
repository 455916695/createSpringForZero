package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 *    BeanDefinitionRegistry (Registry  n. 注册；登记处；挂号处；船舶的国籍)
 * */
public interface BeanDefinitionRegistry {

    BeanDefinition  getBeanDefinition(String beanId);

    void  registerBeanDefinition(String beanID,BeanDefinition beanDefinition);
}
