package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourcesTest {

    @Test
    public void testClassPathResource() throws IOException {

        Resource resource = new ClassPathResource("petstore-v1.xml") ;

        InputStream   inputStream  =  null;

        try {
            inputStream = resource.getInputStream();

            // 注意：这个测试其实并不充分
            Assert.assertNotNull(inputStream);
        }finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws IOException, IllegalAccessException {

        Resource  resource = new FileSystemResource("D:\\develop\\IDEA\\code\\createSpringForZero\\manager-Spring\\src\\test\\resources\\petstore-v1.xml");

        InputStream  inputStream = null;

        try{
            inputStream = resource.getInputStream();
            //注意：其实这个测试并不充分
            Assert.assertNotNull(inputStream);
        }finally {
            if(inputStream != null) {
                inputStream.close();
            }
        }
    }



































}
