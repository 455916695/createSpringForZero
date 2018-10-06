package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.utils.ClassUtils;


import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory,BeanDefinitionRegistry {

    public  final Map<String,BeanDefinition>  beanDefinitionMap = new ConcurrentHashMap<>();   //用于存放 BeanDefinition 的ConcurrentHashMap
    private ClassLoader beanClassLoader;

    public DefaultBeanFactory( ) {
    }

    public void registerBeanDefinition(String beanID, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanID,beanDefinition);  // 将 beanDefinition  存放到Map中
    }

    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    public Object getBean(String beanId)  {
        BeanDefinition beanDefinition  =  this.getBeanDefinition(beanId);
        if(beanDefinition  == null) {
            return null;
        }

        if(beanDefinition.isSingleton()) {
            Object bean = this.getSingleton(beanId);
            if(bean ==null) {
                bean = createBean(beanDefinition);
                try {
                    this.registerSingleton(beanId,bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return bean ;
        }
        return createBean(beanDefinition);
    }

    private  Object createBean (BeanDefinition beanDefinition) {
        //创建实例
        Object bean = instantiateBean(beanDefinition);
        //设置属性
        populateBean(beanDefinition,bean);

        return bean;
    }


    private  Object instantiateBean (BeanDefinition beanDefinition) {
        ClassLoader classLoader = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = classLoader.loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw  new BeanCreationException("create bean for "+beanClassName+" failed",e);
        }
    }

    protected  void  populateBean(BeanDefinition beanDefinition,Object bean) {

        //从beanDefinition  中获取属性列表
        List<PropertyValue> propertyValueList = beanDefinition.getPropertyValues();

        if(propertyValueList == null || propertyValueList.isEmpty()){
            return ;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue propertyValue:propertyValueList){
                String propertyName = propertyValue.getName();
                Object  originalValue = propertyValue.getValue();
                Object resolvedValue = valueResolver.resolverValueIfNecessary(originalValue);  //
                //假设现在OriginalValue 表示的是ref = accountDao ，已经通过resolve得到了accountDao对象
                //接下来怎么办? 如何去调用PetStore 的setAccountDao方法？
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for(PropertyDescriptor propertyDescriptor :propertyDescriptors) {
                    if(propertyDescriptor.getName().equals(propertyName)) {
                        propertyDescriptor.getWriteMethod().invoke(bean,resolvedValue);
                        break;
                    }
                }
                //从BeanFactory 中获取PetStore
            }
        }catch (Exception ex){
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getPropertyValues());
        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
         this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader !=null  ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
    }
}
