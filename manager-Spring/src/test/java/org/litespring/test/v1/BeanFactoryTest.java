package org.litespring.test.v1;

import com.sun.deploy.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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


    @Test
    public void testInvalidBean() {    //Invalid   adj. 无效的；有病的；残废的

        BeanFactory  factory  =  new DefaultBeanFactory("petstore-v1.xml");

        try {
            factory.getBean("invalidBean");
        }catch (BeanCreationException  e){
            return;
        }
        Assert.fail("expect  BeanCreateionException ");  // expect  vt. 期望；指望；认为；预料  vi. 期待；预期

    }

    @Test
    public void  testInvalidXml() {

        try {
            new DefaultBeanFactory("xxx.xml");
        }catch (BeanDefinitionStoreException  e){
            return;
        }
        Assert.fail("expect  BeanDefinitionStoreException ");
    }


    @Test
    public void testString() {
        String str = null;
        int i = lengthOfLongestSubstring(str);
        Assert.assertEquals(0,i);

        String  str2 = "a";
        int i1 = lengthOfLongestSubstring(str2);
        Assert.assertEquals(1,i1);

        String  str3 = "abac";
        int i2 = lengthOfLongestSubstring(str3);
        Assert.assertEquals(3,i2);

        String str4 = "abcabcbb";
        int i3 = lengthOfLongestSubstring(str4);
        Assert.assertEquals(3,i3);

        String str5 ="uqinntq";
        int i5 = lengthOfLongestSubstring(str5);
//        lengthOfLongestSubstring2(str5);
        Assert.assertEquals(4,i5);
    }

    public int lengthOfLongestSubstring(String s) {
        //排除边缘
        if(s == null || s.equals("")) {
            return 0;
        }
        if(s.length() == 1) {
            return 1;
        }

        int  result = 1;
        int  removeIndex = 0;

        ArrayList<Character> arrayList = new ArrayList<>();
//
//        char[] chars = s.toCharArray();

        for(int i = 0; i<s.length();i++ ) {
            int index = arrayList.lastIndexOf( s.charAt(i));
            if(index >=0){
                int size = arrayList.size();
                if(result < size-removeIndex ) {
                    result = size-removeIndex;
                }
                //删除这个索引之前的值
               if(index >= removeIndex || (index ==0 && removeIndex == 0)) {
                    removeIndex  = index + 1;
                }
            }
            arrayList.add(s.charAt(i));
        }

        if(arrayList.size()-removeIndex > result) {
            return  arrayList.size()-removeIndex;
        }
        return result;
    }

    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }
//    public  void  removeMapValue (ArrayList<Character> arrayList,int index) {
//
//        for(index++;0<= --index; ) {
//             arrayList.remove(index);
//        }
//
//    }

}
