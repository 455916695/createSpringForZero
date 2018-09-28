package com.ax;

import org.junit.Assert;
import org.junit.Test;

/**
 *   这是一个测试驱动的案例
 * */
public class PrimeUtilsTest {


    @Test   //测试  获取素数  关于空结果的情况
    public  void  testGetPrimesForEmptyResult() {
        int[]  expected  = {};

        //  分析边界条件
        Assert.assertArrayEquals(expected,PrimeUtil.getPromes(2));     //比较连个数组是否相等
        Assert.assertArrayEquals(expected,PrimeUtil.getPromes(0));
        Assert.assertArrayEquals(expected,PrimeUtil.getPromes(-1));

    }

    @Test   //测试  获取素数  关于正常情况
    public void testGetPrimes() {

        Assert.assertArrayEquals(new int[]{2,3,5,7},PrimeUtil.getPromes(10));     //比较连个数组是否相等
        Assert.assertArrayEquals(new int[]{2,3,5,7,11,13,17,19},PrimeUtil.getPromes(20));
        Assert.assertArrayEquals(new int[]{2,3,5,7,11,13,17,19,23,29},PrimeUtil.getPromes(30));
    }

}
