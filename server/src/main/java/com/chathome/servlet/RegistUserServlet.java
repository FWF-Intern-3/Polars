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
 *
 */
@WebServlet(value = "/RegisterUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        //获取数据
        BufferedReader reader = request.getReader();
        String line=null;
        String json="";
        while ((line=reader.readLine())!=null){
            json+=line;
        }
        ObjectMapper objectMapper=new ObjectMapper();
        User user=objectMapper.readValue(json,User.class);

        //先判断验证码是否正确，在进行注册


        //获取用户输入的验证码
        String client_captcha = user.getCaptcha();
        //从session中获取验证码
        HttpSession session = request.getSession();
        String usercheckcode=(String) session.getAttribute("UserSessionCode");
        request.getSession().removeAttribute("UserSessionCode");
        //响应数据
        String json1="";
        //比较验证码
        if (client_captcha==null||!usercheckcode.equalsIgnoreCase(client_captcha)){
            //验证码错误,重新注册

            json1= MessageUtils.ReturnData_MC("验证码错误",MessageUtils.CODE_ONE);


        }else {
                //验证码正确，开始进行注册
                //调用Service完成注册，收到true/false
                UserService service = new UserServiceImpl();
                boolean flag = service.register(user);

                if (flag){
                    //注册成功
                     json1= MessageUtils.ReturnData_MC("注册成功",MessageUtils.CODE_ZERO);

                 }else {
                    json1= MessageUtils.ReturnData_MC("注册失败",MessageUtils.CODE_ONE);
                }
        //将结果以json格式，响应客户端

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json1);
      }

   }
}
