package com.chathome.dao;

import com.chathome.entity.User;

public interface UserDao {

    public User findByUserEmail(String email);


    public int save(User user);


    public User findByEmailAndPassword(String email, String password);


    public  User findUserName(String email);

}
