package org.litespring.utils;

public class StringUtils {

    public static boolean  hasLength (String str ) {
        return hasLength((CharSequence)str);   //CharSequence 字符序列
    }

    public static boolean hasLength(CharSequence sequence) {
        return (sequence != null && sequence.length()>0);
    }

    public static boolean  hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static boolean hasText(CharSequence str) {
        if(!hasLength(str)){
            return  false;
        }
        int strLen = str.length();
        for(int i=0;i<strLen;i++) {
            if(!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
