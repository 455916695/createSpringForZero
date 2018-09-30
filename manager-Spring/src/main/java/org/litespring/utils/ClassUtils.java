package org.litespring.utils;

/**
 *   ClassUtils
 * */
public class ClassUtils {

    /**
     *   获取默认类加载器
     * */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader  classLoader = null;

        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        }catch (Throwable throwable) {

        }
        if(classLoader == null) {
            classLoader = ClassUtils.class.getClassLoader();    //这个什么操作
            if (classLoader  == null) {
                try {
                    classLoader  = ClassLoader.getSystemClassLoader();
                }catch (Throwable  throwable ){

                }
            }
        }
        return classLoader;
    }
}
