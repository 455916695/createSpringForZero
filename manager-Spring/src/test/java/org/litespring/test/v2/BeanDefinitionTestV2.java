package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;

import java.util.List;

public class BeanDefinitionTestV2 {

    @Test
    public void testGetBeanDefinition() {

        DefaultBeanFactory factory = new DefaultBeanFactory();   //创建一个默认的 BeanFactory
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);   //创建一个XmlBeanDefinitionReader对象

        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));  //加载Bean描述

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");   //从BeanFactory中获取

        List<PropertyValue> propertyValues =  beanDefinition.getPropertyValues();

        Assert.assertTrue(propertyValues.size() == 2);
        {
            PropertyValue propertyValue = this.getPropertyValue("accountDao",propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue()  instanceof RuntimeBeanReference);
        }
        {
            PropertyValue propertyValue =  this.getPropertyValue("itemDao",propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue()  instanceof  RuntimeBeanReference);
        }
    }

    private PropertyValue getPropertyValue(String name,List<PropertyValue> propertyValues) {

        for (PropertyValue propertyValue : propertyValues){
            if(propertyValue.getName().equals(name)) {
                return propertyValue;
            }
        }
        return null;
    }

}
