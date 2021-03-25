package com.webchat.service.impl;


import com.webchat.dao.UserDao;
import com.webchat.dao.impl.UserDaoImpl;
import com.webchat.entity.User;
import com.webchat.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao dao =new UserDaoImpl();

    /**
     * 检查邮箱是否注册过
     * @param user
     * @return
     */
    @Override
    public boolean checkEmail(User user) {
        //根据用户的邮箱查询用户对象
        User byUserEmail = dao.findByUserEmail(user.getEmail());
        //判断用户是否存在(没有注册记录)，不存在才能注册
        if (byUserEmail!=null){
            //用户名存在，注册失败，

           return false;
        }

        return true;
    }

    /**
     * 根据登录邮箱，找到用户，返回用户（上一级要使用用户名）
     * @param user
     * @return  user
     */
    @Override
    public User ReturnUsername(User user) {
      User user1=dao.findUserName(user.getEmail());
       return user1;
    }

    /**
     * 保存用户输入的用户名
     * @param user
     * @return
     */
    @Override
    public boolean SaveUserName(User user) {
        int i = dao.ByEmailSaveUsername(user);
        if (i==0){
            return false;
        }
        return true;
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        int save = dao.save(user);
        if (save==0){
            return false;
        }
        return true;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User byEmailAndPassword = dao.findByEmailAndPassword(user.getEmail(), user.getPassword());
        return byEmailAndPassword;
    }
}


