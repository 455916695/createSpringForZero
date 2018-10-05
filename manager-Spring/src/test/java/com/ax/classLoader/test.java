package com.ax.classLoader;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.Resource;

public class test   {


    @Test
    public void classLoaderTest() {
        //  根装载器（c++ 语言编写的）   ExtClassLoader（扩展类装载器）   AppClassLoader（应用类装载器）（ 默认使用Application）
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Assert.assertNotNull(contextClassLoader);
        Assert.assertNotNull(contextClassLoader=contextClassLoader.getParent());
        Assert.assertNull(contextClassLoader=contextClassLoader.getParent());

    }

}
