package com.webchat.dao.impl;

import com.webchat.dao.UserDao;
import com.webchat.entity.User;
import com.webchat.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查看数据库中有没有这个邮箱
     * @param email
     * @return
     */
    @Override
    public User findByUserEmail(String email) {

        User user=null;//用于判断是否封装成功，不成功说明用户存在，返回null;
        try{
        //sql语句
        String sql="select * from user where email=?";
        //执行sql
           user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);
        }catch (Exception e){
            //没有封装成功，说明没有赋值成功（结果是null）
        }

        return user;
    }

    /**
     * 保存用户信息
     * @param user
     */
    @Override
    public int save(User user) {
        //sql语句int
        String sql="insert into user(email,password) value(?,?)";
        //执行sql
        int update = template.update(sql, user.getEmail(), user.getPassword());
       return update;
    }

    /**
     * 根据邮箱保存用户名
     * @param user
     * @return
     */
    @Override
    public  int ByEmailSaveUsername(User user){
        //sql语句
        String sql="update user set username=? where email=?";
        int count=template.update(sql,user.getUsername(),user.getEmail());
        return count ;
    }

    /**
     * 登录是根据用户输入的邮箱和密码查询是否匹配
     * @param email   邮箱
     * @param password   密码
     * @return
     */
    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user=null;
        try {
            //sql语句
            String sql="select * from user where email=? and password=?";
            //执行sql

            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email, password);
        }catch (Exception e){

        }
        return user;
    }

    /**
     *   根据邮箱 查询用户名
     * @param email
     * @return  user
     */
    @Override
    public User findUserName(String email) {
     User user=new User();
        try {
            //sql语句
            String sql="select username from user where email=?";
            //执行sql

            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), email);
        }catch (Exception e){
             e.printStackTrace();
        }
        return user;
    }
}
