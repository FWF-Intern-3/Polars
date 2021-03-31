package com.chathome.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装好响应的数据
 *
 */
public class MessageUtils {
       //需要返回的消息
        public final static String CODE_ZERO="0";
        public final static String CODE_ONE="1";
        public final static String CODE_TWO="2";
        public final static String CODE_THREE="3";
        public final static String CODE_FOUR="4";
        public final static String CODE="code";//
        public final static String DATA="data";//
        public final static String FROM_NAME="fromName";//发送者用户名
        public final static String TO_NAME="toName";//接收者用户名

        public final static String MSG="msg";
        public final static String USERNAME="username";


        //发送聊天内容
        public static String getContent(Object code,String fromName,String toName,String content){
            Map<String,Object> userMap=new HashMap<String,Object>();
            userMap.put(MessageUtils.CODE,code);
            userMap.put(MessageUtils.DATA,content);
            userMap.put(MessageUtils.FROM_NAME,fromName);
            userMap.put(MessageUtils.TO_NAME,toName);

            String json= JSON.toJSONString(userMap);
            return json;
        }

       //发送用户名列表
         public static String getNames(Object code, List<String> names){
            Map<String,Object> userMap=new HashMap<String,Object>();
            userMap.put(MessageUtils.CODE,code);
            userMap.put(MessageUtils.DATA,names);


            String json= JSON.toJSONString(userMap);
            return json;
        }
        //返回给前端对应的mag 和 code值
       public static String ReturnData_MC(String msg,Object code){
           Map<String,Object> userMap=new HashMap<String,Object>();
           userMap.put(MessageUtils.MSG,msg);
           userMap.put(MessageUtils.CODE,code);

           String json= JSON.toJSONString(userMap);
           return json;
       }

    //返回给前端对应的mag,code,username值
    public static String ReturnData_MCU(String msg,Object code,String username){
        Map<String,Object> userMap=new HashMap<String,Object>();
        userMap.put(MessageUtils.MSG,msg);
        userMap.put(MessageUtils.CODE,code);
        userMap.put(MessageUtils.USERNAME,username);

        String json= JSON.toJSONString(userMap);
        return json;
    }



}


