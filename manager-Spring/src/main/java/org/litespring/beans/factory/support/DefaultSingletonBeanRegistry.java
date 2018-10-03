package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.utils.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String,Object> singletonObjects = new ConcurrentHashMap<String,Object>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) throws IllegalAccessException {

        Assert.notNull(beanName,"'beanName' must not be null");

        Object oldObject = this.singletonObjects.get(beanName);

        if(oldObject != null) {
            throw new IllegalAccessException("Could not register object [" + singletonObject + "] under bean name '"+beanName +"': there is already object ["+oldObject+"]"  );
        }
        this.singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
