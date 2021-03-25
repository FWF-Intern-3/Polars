package com.webchat.util;


import com.webchat.dao.UserDao;
import com.webchat.dao.impl.UserDaoImpl;
import com.webchat.entity.User;
import com.webchat.service.UserService;
import com.webchat.service.impl.UserServiceImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class text {
   public static List<String> getNames()
    {

        List<String> list=new ArrayList<String>();

        for (int i = 0; i < 6; i++) {
            String username="aaaaaaaaa";
            list.add(username);

        }


        return list;

    }
    public static void main(String[] args) {


        //获取当前所有登录用户
        List<String> names=getNames();

        //组装消息
        String message = MessageUtils.getNames( MessageUtils.CODE_THREE,names);
        System.out.println(message);
        String S="{\n" +
                "    \"email\":\"Zzrt4396@163.com\"\n" +
                "}";
    }


}
