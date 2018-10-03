package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.utils.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 *
 * */
public class XmlBeanDefinitionReader {

    public  static  final  String ID_ATTRIBUTE = "id";

    public static  final String  CLASS_ATTRIBUTE = "class";

    public static final String  SCOPE_ATTRIBUTE ="scope";

    BeanDefinitionRegistry registry;

    public  XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void  loadBeanDefinitions(Resource resource) {
        InputStream  inputStream = null;
        try {

//            ClassLoader  classLoader = ClassUtils.getDefaultClassLoader();    //通过ClassUtils 工具类  获取  ClassLoader
//            inputStream = classLoader.getResourceAsStream(configFile);        // 调用classLoader的getResurceAsStream()获取输入流对象

            inputStream  =  resource.getInputStream();

            SAXReader  saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            Element rootElement = document.getRootElement();
            Iterator iterator = rootElement.elementIterator();
            while (iterator.hasNext()){
                Element element = (Element) iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition  beanDefinition = new GenericBeanDefinition(id,beanClassName);
                if(element.attribute(SCOPE_ATTRIBUTE) !=null) {
                    beanDefinition.setScope(element.attributeValue(SCOPE_ATTRIBUTE));
                }
                this.registry.registerBeanDefinition(id,beanDefinition);
            }

        } catch (DocumentException e) {
           throw new BeanDefinitionStoreException("IOException  parsing  XML document",e);
        } catch (IOException e) {
            throw new BeanDefinitionStoreException("IOException  parsing  XML document",e);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
