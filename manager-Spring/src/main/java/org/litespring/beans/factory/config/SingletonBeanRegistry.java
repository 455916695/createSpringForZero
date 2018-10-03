package org.litespring.beans.factory.config;

import sun.rmi.transport.ObjectTable;

public interface SingletonBeanRegistry {

    void  registerSingleton(String  beanName, Object singletonObject) throws IllegalAccessException;

    Object getSingleton(String beanName);
}
