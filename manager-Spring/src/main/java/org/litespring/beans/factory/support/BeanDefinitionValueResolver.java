package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

public class BeanDefinitionValueResolver {

    private  final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory defaultBeanFactory) {
            this.beanFactory = defaultBeanFactory;
    }

    public Object resolverValueIfNecessary(Object value){

        if(value instanceof RuntimeBeanReference) {

            RuntimeBeanReference reference = (RuntimeBeanReference) value;
            String beanName = reference.getBeanName();
            Object bean = this.beanFactory.getBean(beanName);
            return  bean;

        } else if(value instanceof TypedStringValue){
            return ((TypedStringValue)value).getValue();
        }else {
            //TODO
            throw  new RuntimeException("the value "+value+" has not implemented");
        }
    }
}
