package com.chathome.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.chathome.entity.User;
import com.chathome.service.UserService;
import com.chathome.service.impl.UserServiceImpl;
import com.chathome.util.MessageUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

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

        String json1="";
        if (user1==null){
            //邮箱或密码错误
            json1= MessageUtils.ReturnData_MC("邮箱或密码错误",MessageUtils.CODE_ONE);

        }else {
            //正确，登录成功
            UserService userService=new UserServiceImpl();
            User user2=userService.ReturnUsername(user);

            json1= MessageUtils.ReturnData_MCU("登录成功",MessageUtils.CODE_ZERO,user2.getUsername());
            //保存用户消息在Session，群聊需要
            HttpSession httpSession=request.getSession();
            httpSession.setAttribute("username",user2.getUsername());



        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json1);

    }
}
