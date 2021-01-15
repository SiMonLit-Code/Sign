package com.sign.utils;


public class FunctionApplication {
    //判断字符串是否纯数字
    public static boolean toStringGid(String gid){
        char[] chars = gid.toCharArray();
        if (chars.length!=10 && chars.length!=14){
            return false;
        }
        for (int i=0;i<chars.length;i++){
            if (chars[i]>57 || chars[i] < 48){
                return false;
            }
        }
        return true;
    }

    //判断长度
    public static boolean posLength(String len){
            if (len.length()==6 || len.length()==11){
                return true;
            }
        return false;
    }

}
