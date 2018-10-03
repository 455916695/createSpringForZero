package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.utils.ClassUtils;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory,BeanDefinitionRegistry {

    public  final Map<String,BeanDefinition>  beanDefinitionMap = new ConcurrentHashMap<>();   //用于存放 BeanDefinition 的ConcurrentHashMap
    private ClassLoader beanClassLoader;

    public DefaultBeanFactory( ) {
    }

    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID,beanDefinition);  // 将 beanDefinition  存放到Map中
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId)  {
        BeanDefinition beanDefinition  =  this.getBeanDefinition(beanId);
        if(beanDefinition  == null) {
            return null;
        }

        if(beanDefinition.isSingleton()) {
            Object bean = this.getSingleton(beanId);
            if(bean ==null) {
                bean = createBean(beanDefinition);
                try {
                    this.registerSingleton(beanId,bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return bean ;
        }
        return createBean(beanDefinition);
    }

    private  Object createBean (BeanDefinition beanDefinition) {
        ClassLoader classLoader = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = classLoader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw  new BeanCreationException("create bean for "+beanClassName+" failed",e);
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
         this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader !=null  ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }
}
