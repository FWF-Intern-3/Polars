package com.webchat.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.entity.ResultDate;
import com.webchat.entity.User;
import com.webchat.service.UserService;
import com.webchat.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 登录操作
 */
@WebServlet(value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
//        Map<String, String[]> map = request.getParameterMap();
//        //封装数据
//        User user=new User();
//
//        try {
//            BeanUtils.populate(user,map);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        //获取信息
        BufferedReader reader = request.getReader();
        String line=null;
        String json="";
        while ((line=reader.readLine())!=null){
            json+=line;
        }
        ObjectMapper objectMapper=new ObjectMapper();
        User user=objectMapper.readValue(json,User.class);

        //调用service查询用户是否存在
        UserService service=new UserServiceImpl();
        User user1=service.login(user);

        ResultDate resultDate=new ResultDate();
        if (user1==null){
            //邮箱或密码错误
           resultDate.setCode(1);
           resultDate.setMsg("邮箱或密码错误");

        }else {
            //正确，登录成功
            resultDate.setCode(0);
            resultDate.setMsg("登录成功");

            UserService userService=new UserServiceImpl();
            User user2=userService.ReturnUsername(user);

            resultDate.setUsername(user2.getUsername());
            //保存用户消息在Session，群聊需要
            request.getSession().setAttribute("username",user2.getUsername());


        }
        //响应数据
        ObjectMapper mapper=new ObjectMapper();
        String json1=mapper.writeValueAsString(resultDate);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json1);

    }
}
