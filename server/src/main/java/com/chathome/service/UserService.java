package com.chathome.service;

import com.chathome.entity.User;

public interface UserService {
    boolean register(User user);//注册
    User login(User user);   //登录
    boolean checkEmail(User user);//邮箱验证
    User ReturnUsername(User user);//


}
