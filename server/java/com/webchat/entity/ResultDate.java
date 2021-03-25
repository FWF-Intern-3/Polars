package com.webchat.entity;

/**
 * 封装好数据，返回给前端
 */
public class ResultDate  {

    //后端返回结果的数据对象
    private Object data;
    //响应的信息
    private String msg;
    //返回值
    private int code;
    //
    private String username;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ResultDate(Object data, String msg, int code, String username) {
        this.data = data;
        this.msg = msg;
        this.code = code;
        this.username = username;
    }

    public ResultDate() {
    }

    @Override
    public String toString() {
        return "ResultDate{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", username='" + username + '\'' +
                '}';
    }
}
