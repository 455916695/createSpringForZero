package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

/**
 *
 * */
public class BeanFactoryTest {

    @Test
    public void testGetBean() {

        BeanFactory beanFactory  =  new DefaultBeanFactory("petstore-v1.xml");

        BeanDefinition beanDefinition  = beanFactory.getBeanDefinition("petStore");  // .definition  定义；[物] 清晰度；解说

        Assert.assertEquals("org.litespring.service.v1.PetStoreService",beanDefinition.getBeanClassName());

        PetStoreService  petStoreService =  (PetStoreService)beanFactory.getBean("petStore");

        Assert.assertNotNull(petStoreService);
    }

}
