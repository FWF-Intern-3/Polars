package com.webchat.web.servlet;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.entity.ResultDate;
import com.webchat.entity.User;
import com.webchat.service.UserService;
import com.webchat.service.impl.UserServiceImpl;
import com.webchat.util.MailUtils;
import com.webchat.util.codeUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 验证码的发送
 */
@WebServlet(value = "/CheckCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的邮箱
        BufferedReader reader = request.getReader();
        String line=null;
        String json="";
        while ((line=reader.readLine())!=null){
            json+=line;
        }
        ObjectMapper objectMapper=new ObjectMapper();
        User user=objectMapper.readValue(json,User.class);


        //生成验证码
        String user_code= codeUtils.code();
        //将验证码保存到Session
        request.getSession().setAttribute("UserSessionCode",user_code);

         /**
         * 发送验证码
         */


        //检查用户邮箱是否存在

        //调用Service完成邮箱验证，收到true/false
        UserService service=new UserServiceImpl();
        boolean flag = service.checkEmail(user);
        //响应结果,是否发送成功
        ResultDate resultDate=new ResultDate();
        if (flag){
            //注册成功
            String content="Hey! "+user.getEmail()+"\r"+
                            "欢迎注册Polars聊天室。\r"+
                            "验证码:"+user_code+"\r"+
                            "如果您没有申请邮箱验证，请忽略此邮件。"
                             ;

            MailUtils.sendMail(user.getEmail(),content,"Polars邮箱验证");
            resultDate.setCode(0);
            resultDate.setMsg("邮箱发送成功");
        }else {
            //注册失败
            resultDate.setCode(1);
            resultDate.setMsg("邮箱已注册");
        }

        ObjectMapper mapper=new ObjectMapper();
        String json1=mapper.writeValueAsString(resultDate);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json1);







    }
}
