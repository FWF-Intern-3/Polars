package com.webchat.util;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.util.Properties;

/**
 * 发送邮箱的工具类
 */
public class MailUtils {
    private static final String USER="2970539535@qq.com";//发件人邮箱
    private static final String PASSWORD="rvcdqkudmxvkdgge";//授权码，密码

    /**
     *
     * @param to  收件人邮箱
     * @param text  邮件正文
     * @param title  标题
     * @return
     */
    public static void sendMail(String to,String text,String title)  {
        try {
            final Properties props=new Properties();
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.host","smtp.qq.com");

            //发件人的账号
            props.put("mail.user",USER);
            //发件人密码
            props.put("mail.password",PASSWORD);

            //构建授权信息，用于SMTP验证
            Authenticator authenticator=new Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    //用户名，密码
                    String userName=props.getProperty("mail.user");
                    String password=props.getProperty("mail.password");

                    return new PasswordAuthentication(userName,password);
                }
            };
            //使用环境属性和授权信息，创建邮件会话
            Session mailSession=Session.getDefaultInstance(props,authenticator);
            //创建邮件消息
            MimeMessage message=new MimeMessage(mailSession);
            //设置发件人
            String username=props.getProperty("mail.user");
            InternetAddress from=new InternetAddress(username);
            message.setFrom(from);
            //设置收件人
            InternetAddress toAddress=new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO,toAddress);
            //设置邮件标题
            message.setSubject(title);
            //设置邮件的内容体
            message.setContent(text,"text/html;charset=UTF-8");
            //发送邮件
            Transport.send(message);

        }catch (Exception e){
            e.printStackTrace();
        }

    }



}

