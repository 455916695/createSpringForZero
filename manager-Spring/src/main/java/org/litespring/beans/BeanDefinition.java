package org.litespring.beans;

import java.util.List;

public interface BeanDefinition {

    public static  final String SCOPE_SINGLETON = "singleton";

    public static final  String  SCOPE_PROTOTYPE = "prototype";

    public static final String  SCOPE_DEFAULT = "";

    public boolean  isSingleton();

    public boolean  isPrototype();

    String getScope();

    void setScope(String s);

    public String getBeanClassName();

    //获取 属性对象列表  <property  name=""  ref="" /> <property  name=""  value="" />
    List<PropertyValue> getPropertyValues();
}
