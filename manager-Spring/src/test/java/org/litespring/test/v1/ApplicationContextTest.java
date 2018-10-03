package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService  petStoreService = (PetStoreService) context.getBean("petStore");
        Assert.assertNotNull(petStoreService);
    }

    @Test
    public void  testGetBeanFromFileSystemContext() {
        //注意: 这里仍然是hardcode了一个本地路径，这是不好的实践!!如何处理，留作业
        ApplicationContext  applicationContext = new FileSystemXmlApplicationContext("D:\\develop\\IDEA\\code\\createSpringForZero\\manager-Spring\\src\\test\\resources\\petstore-v1.xml");

        PetStoreService  petStoreService = (PetStoreService) applicationContext.getBean("petStore");

        Assert.assertNotNull(petStoreService);
    }

}
