package com.sign.function;

import org.springframework.stereotype.Component;

@Component
public class FunctionApplication {
    //判断字符串是否纯数字
    public boolean toStringGid(String gid){
        char[] chars = gid.toCharArray();
        if (chars.length!=10 && chars.length!=14){
            return false;
        }
        for (int i=0;i<chars.length;i++){
            if (chars[i]>57 || chars[i] < 48){
                return false;
            }
//            System.out.println("["+(int)chars[i]+"]");
        }
        return true;
    }

    //判断长度
    public boolean posLength(String len){
            if (len.length()==6 || len.length()==11){
                return true;
            }
        return false;
    }


}
