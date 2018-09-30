package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.utils.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

    public static final String  ID_ATTRIBUTE  = "id";        //定义的常量  id

    public static  final String  CLASS_ATTRIBUTE =  "class";   //定义的常量  class

    public  final Map<String,BeanDefinition>  beanDefinitionMap = new ConcurrentHashMap<>();   //用于存放 BeanDefinition 的ConcurrentHashMap

    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);    // 解析配置文件，生成Bean描述对象
    }

    private void loadBeanDefinition(String configFile) {
        InputStream  inputStream = null;
        try {
            ClassLoader  classLoader = ClassUtils.getDefaultClassLoader();    //通过ClassUtils 工具类  获取  ClassLoader
            inputStream = classLoader.getResourceAsStream(configFile);        // 调用classLoader的getResurceAsStream()获取输入流对象

            SAXReader  saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            Element rootElement = document.getRootElement();
            Iterator<Element> iterator = rootElement.elementIterator();     //获取根元素的迭代，遍历根节点中每一个节点
            while (iterator.hasNext()) {
                Element element = iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String beanClassName = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition   beanDefinition = new CenericBeanDefinition(id,beanClassName);
                this.beanDefinitionMap.put(id,beanDefinition);      //  将以id为键，beanDefinition对象为value，，存放到beanDefinitionMap
            }
        }  catch (DocumentException e) {
            throw  new BeanDefinitionStoreException("IOException  parsing  Xml  document",e);  //   parsing   句法分析分析解析语法分析
        }finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition  =  this.getBeanDefinition(beanId);
        if(beanDefinition  == null) {
            return null;
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

        String beanClassName  = beanDefinition.getBeanClassName();

        try {
            Class<?> loadClass = classLoader.loadClass(beanClassName);
            return  loadClass.newInstance();     //利用反射生成  相应的对象 （ instance n. 实例；情况；建议，vt. 举...为例）
        } catch (Exception e) {
            throw  new BeanCreationException("create  bean  for "+beanClassName + "failed",e);
        }

    }
}
