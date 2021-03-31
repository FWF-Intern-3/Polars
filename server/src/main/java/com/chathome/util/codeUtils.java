package com.chathome.util;



import java.util.Random;

/**
 * 生成6位数验证码的工具类
 */
public class codeUtils {
    private static String str="0123456789";

    public static String code(){
    String code="";
        Random ran=new Random();

        for (int i = 0; i <=5; i++) {
            int index=ran.nextInt(str.length());
            //获取一个字符
            char ch=str.charAt(index);
            //拼接字符
            code+=ch;
        }
       return code;
    };



}
