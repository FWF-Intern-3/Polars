package com.chathome.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.chathome.entity.User;
import com.chathome.service.UserService;
import com.chathome.service.impl.UserServiceImpl;
import com.chathome.util.MailUtils;
import com.chathome.util.MessageUtils;
import com.chathome.util.codeUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

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

        //响应数据
        String json1="";
        if (flag){
            //注册成功
            String content="Hey! "+user.getEmail()+
                            "欢迎注册Polars聊天室。"+
                            "验证码:"+user_code+""
                             ;

            MailUtils.sendMail(user.getEmail(),content,"Polars邮箱验证");

            json1= MessageUtils.ReturnData_MC("邮箱发送成功",MessageUtils.CODE_ZERO);
        }else {
            //注册失败

            json1= MessageUtils.ReturnData_MC("邮箱已注册",MessageUtils.CODE_ONE);
        }


        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json1);







    }
}
