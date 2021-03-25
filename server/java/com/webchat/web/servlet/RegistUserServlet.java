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


        // 获取用户输入的邮箱
//        Map<String,String[]> map=request.getParameterMap();
//        User user=new User();
        //封装数据
//        try {
//            BeanUtils.populate(user,map);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }



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

        ResultDate resultDate=new ResultDate();
        //比较验证码
        if (client_captcha==null||!usercheckcode.equalsIgnoreCase(client_captcha)){
            //验证码错误,重新注册

            resultDate.setCode(1);
            resultDate.setMsg("验证码错误");

        }else {
            //验证码正确，开始进行注册

            //调用Service完成注册，收到true/false
            UserService service = new UserServiceImpl();
            boolean flag = service.register(user);


            //注册成功
            resultDate.setCode(0);
            resultDate.setMsg("注册成功");
            //保存邮箱，之后用来保存用户名
            request.getSession().setAttribute("EMAIL", user.getEmail());

        }
        //将结果以json格式，响应客户端
        ObjectMapper mapper=new ObjectMapper();
        String json1=mapper.writeValueAsString(resultDate);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json1);
      }

}
