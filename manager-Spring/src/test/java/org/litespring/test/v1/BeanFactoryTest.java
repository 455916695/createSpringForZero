package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v1.PetStoreService;


/**
 *
 * */
public class BeanFactoryTest {

    private DefaultBeanFactory beanFactory = null;

    private  XmlBeanDefinitionReader reader = null;
    @Before
    public void setUp () {
        beanFactory  =  new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(beanFactory);

    }

    @Test
    public void testGetBean() {

//        BeanFactory beanFactory  =  new DefaultBeanFactory("petstore-v1.xml");

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
        BeanDefinition beanDefinition  = beanFactory.getBeanDefinition("petStore");  // .definition  定义；[物] 清晰度；解说

        Assert.assertTrue(beanDefinition.isSingleton());
        Assert.assertFalse(beanDefinition.isPrototype());

        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT,beanDefinition.getScope());
        Assert.assertEquals("org.litespring.service.v1.PetStoreService",beanDefinition.getBeanClassName());


        PetStoreService  petStoreService =  (PetStoreService)beanFactory.getBean("petStore");

        Assert.assertNotNull(petStoreService);

        PetStoreService  petStoreService1 = (PetStoreService) beanFactory.getBean("petStore");
        Assert.assertTrue(petStoreService.equals(petStoreService1));
    }


    @Test
    public void testInvalidBean() {    //Invalid   adj. 无效的；有病的；残废的

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));       //  此处  曾经存在灵异bug

        try {
            beanFactory.getBean("invalidBean");
        }catch (BeanCreationException  e){
            return;
        }
        Assert.fail("expect  BeanCreateionException ");  // expect  vt. 期望；指望；认为；预料  vi. 期待；预期

    }

    @Test
    public void  testInvalidXml() {

        try {

            reader.loadBeanDefinitions(new ClassPathResource("xxxx-v1.xml"));
        }catch (BeanDefinitionStoreException  e){
            return;
        }
        Assert.fail("expect  BeanDefinitionStoreException ");
    }


}
