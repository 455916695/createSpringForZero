package org.litespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.utils.StringUtils;

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

    public static final String  PROPERTY_ELEMENT = "property";

    public static final String REF_ATTRIBUTE = "ref";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String NAME_ATTRIBUTE = "name";

    public final Log logger = LogFactory.getLog(getClass());

    BeanDefinitionRegistry registry;

    public  XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void  loadBeanDefinitions(Resource resource) {
        InputStream  inputStream = null;
        try {
//            ClassLoader  classLoader = ClassUtils.getDefaultClassLoader();    //通过ClassUtils 工具类  获取  ClassLoader
//            inputStream = classLoader.getResourceAsStream(configFile);        // 调用classLoader的getResurceAsStream()获取输入流对象
            inputStream  =  resource.getInputStream();                      //调用Resource 的getInputStream()  获取 相应的输入流
            SAXReader  saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);               //使用saxReader.read()   获取Document对象

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
                parsePropertyElement(element,beanDefinition); //解析指定element  并将解析出来的数据存放到beanDefinition
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
    /**
     *  解析属性元素
     * */
    public void parsePropertyElement(Element beanElem,BeanDefinition beanDefinition){
        Iterator iterator = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()) {
            Element propElem  = (Element) iterator.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);  //获取属性name值
            if(!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag  'property' must have a 'name' attribute");
                return ;
            }

            Object val = parsePropertyValue(propElem,beanDefinition,propertyName);   //解析属性的值
            PropertyValue propertyValue = new PropertyValue(propertyName,val);  //创建一个PropertyValue的对象

            beanDefinition.getPropertyValues().add(propertyValue);   //将 propertyValue添加到 beanDefinition中的属性列表中
        }
    }

    /**
     *  解析属性值
     * */
    public Object parsePropertyValue(Element element ,BeanDefinition beanDefinition,String propertyName){
        //  判断   这个后续操作没有看懂？？？ TODO  存在疑惑的地方
        String elementName = (propertyName != null) ?  "<property> element for property '" +propertyName+"'":"<constructor-arg> element";

        boolean hasRefAttribute = (element.attribute(REF_ATTRIBUTE) != null);  //从获取元素中获取ref属性，并判断是否为null
        boolean hasValueAttribute = (element.attribute(VALUE_ATTRIBUTE) != null);  //从获取元素中获取value属性，并判断是否为null

        if(hasRefAttribute) {    // 其实质就是判断  <property> 标签中是否存在ref属性
            String refName = element.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasText(refName)) {
                logger.error(elementName + " contains  empty  'ref' attribute");
            }
            RuntimeBeanReference reference = new RuntimeBeanReference(refName);
            return reference;
        }else if(hasValueAttribute) {
            TypedStringValue valueHolder = new TypedStringValue(element.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        }else {
            throw  new RuntimeException(elementName + " must specify a ref or value");
        }
    }


}



