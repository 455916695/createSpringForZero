package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

public class CustomNumberEditorTest {

    @Test
    public void testConvertString () {

        CustomNumberEditor customNumberEditor = new CustomNumberEditor(Integer.class,true);  //参数的含义？

        customNumberEditor.setAsText("3");

        Object value = customNumberEditor.getValue();
        Assert.assertTrue(value instanceof  Integer);
        Assert.assertEquals(3,((Integer)value).intValue());

        customNumberEditor.setAsText("");

        value = customNumberEditor.getVlaue();
        Assert.assertTrue(value == null);

        try{
            customNumberEditor.setAsText("3.1");
        }catch (IllegalArgumentException e){
            return ;
        }
        Assert.fail(" should throw IllegalArgumentException");

    }

}
