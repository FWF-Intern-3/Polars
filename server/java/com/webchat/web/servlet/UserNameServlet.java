package com.webchat.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.entity.ResultDate;
import com.webchat.entity.User;
import com.webchat.service.UserService;
import com.webchat.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * 1.注册成功后，跳转到输入用户名界面，保存用户名
 * 2.此接口，也可用来修改用户名
 */
@WebServlet(value = "/UserServlet")
public class UserNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user=new User();

        //保存信息(用户名)
        BufferedReader reader = request.getReader();
        String line=null;
        String json="";
        while ((line=reader.readLine())!=null){
            json+=line;
        }
        ObjectMapper objectMapper=new ObjectMapper();
        user=objectMapper.readValue(json,User.class);


        HttpSession session = request.getSession();
        String UserEmail=(String) session.getAttribute("EMAIL");
        user.setEmail(UserEmail);
        UserService userService=new UserServiceImpl();

        boolean b = userService.SaveUserName(user);
        ResultDate resultDate=new ResultDate();
        if (b==false){
            resultDate.setCode(1);
            resultDate.setMsg("用户名保存失败");

        }else {
            resultDate.setCode(0);
            resultDate.setMsg("用户名保存成功");
        }


        ObjectMapper mapper=new ObjectMapper();
        String json1=mapper.writeValueAsString(resultDate);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json1);


    }
}