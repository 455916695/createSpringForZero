package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;

public class ClassPathXmlApplicationContext  implements ApplicationContext{

    private DefaultBeanFactory factory = null;       //   ApplicationContext  继承了BeanFactory接口,  ClassPathXmlApplicationContext 实现了这个接口，且组合DefaultBeanFactory


    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader  reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(configFile);
    }

    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }

}
