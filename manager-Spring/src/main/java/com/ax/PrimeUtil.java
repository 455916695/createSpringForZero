package com.ax;

import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PrimeUtil {

    /***
     *   第一步 ：经过思考设计，编写测试用例
     *   第二步：运行测试用例让其失败
     *   第三步: 编写  just  enough  的代码通过测试用例
     *   第四步： 重构代码
     *
     * */

    /**
     *   在输入max内，寻找所有的素数
     * */
    public static int[] getPromes(int max) {

        if(max <= 2) {
            // max  小于等于2  直接返回空的数组
            return new int[]{};
        }else {
            int[] promesArray = new int[max];
            int  count = 0;
            //max  大于2  就循环遍历这个数
            for(int num=2; num<max ; num++) {
                //调用方法判断是否是素数
                if( isPromes(num)) {
                    promesArray[count++] =  num;
                }
            }

            promesArray = Arrays.copyOf(promesArray, count);
            return  promesArray;
        }

    }


    /**
     *   判断数字是否是素数
     * */
    public  static  boolean isPromes(int  num) {
        for(int i=2;i< num/2+1;i++) {
            if(num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
