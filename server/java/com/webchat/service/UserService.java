package com.webchat.service;

import com.webchat.entity.User;

public interface UserService {
    boolean register(User user);
    User login(User user);
    boolean checkEmail(User user);
    User ReturnUsername(User user);
    boolean SaveUserName(User user);

}
