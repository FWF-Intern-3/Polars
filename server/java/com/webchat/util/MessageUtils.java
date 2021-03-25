package com.webchat.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtils {




        //需要返回的消息
        public final static String CODE_ONE="1";
        public final static String CODE_TWO="2";
        public final static String CODE_THREE="3";
        public final static String CODE="code";
        public final static String DATA="data";
        public final static String FROM_NAME="fromName";
        public final static String TO_NAME="toName";

        //返回消息内容
        public static String getContent(String code,String fromName,String toName,String content){
            Map<String,Object> userMap=new HashMap<String,Object>();
            userMap.put(MessageUtils.CODE,code);
            userMap.put(MessageUtils.DATA,content);
            userMap.put(MessageUtils.FROM_NAME,fromName);
            userMap.put(MessageUtils.TO_NAME,toName);


            String json= JSON.toJSONString(userMap);
            return json;
        }

       //返回给前端用户名
         public static String getNames(String code, List<String> names){
            Map<String,Object> userMap=new HashMap<String,Object>();
            userMap.put(MessageUtils.CODE,code);
            userMap.put(MessageUtils.DATA,names);



            String json= JSON.toJSONString(userMap);
            return json;
    }



}


