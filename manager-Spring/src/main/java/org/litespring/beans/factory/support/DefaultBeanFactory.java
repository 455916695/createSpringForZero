package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.utils.ClassUtils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory,BeanDefinitionRegistry {

    public  final Map<String,BeanDefinition>  beanDefinitionMap = new ConcurrentHashMap<>();   //用于存放 BeanDefinition 的ConcurrentHashMap

    public DefaultBeanFactory( ) {
    }

    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID,beanDefinition);  // 将 beanDefinition  存放到Map中
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition  =  this.getBeanDefinition(beanId);
        if(beanDefinition  == null) {
            return null;
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

        String beanClassName  = beanDefinition.getBeanClassName();

        try {
            Class<?> loadClass = classLoader.loadClass(beanClassName);
            return  loadClass.newInstance();     //利用反射生成  相应的对象 （ instance n. 实例；情况；建议，vt. 举...为例）
        } catch (Exception e) {
            throw  new BeanCreationException("create  bean  for "+beanClassName + "failed",e);
        }

    }
}
