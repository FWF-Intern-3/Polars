package com.webchat.web.websocket;

import com.alibaba.fastjson.JSON;
import com.webchat.util.MessageUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//configurator获取当前用户的HttpSession
@ServerEndpoint(value = "/websocket",configurator =GetHttpSessionConfigurator.class)

public class ChatHome extends Endpoint {
    //websocket会话
    private Session session;
    //保存当前用户的登录HttpSession信息和对应的Endpoint(会话)实例
    private static Map<HttpSession, ChatHome> onLinUsers = new HashMap<HttpSession, ChatHome>();
    //servlet中的Session
    private HttpSession httpSession;
    //记录当前用户登录的人数
    private static int onlineCount = 0;



    /**
     * 建立会话websocket
     *
     * @param session        websocket会话
     * @param endpointConfig
     */
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        //记录socket的会话信息对象session
        this.session = session;
        //获取当前用户登录的HttpSession信息
        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //记录用户登录信息，及对应的Endpoint实例
        onLinUsers.put(httpSession, this);
        //记录当前用户登录数
        addCount();
        //获取当前所有登录用户
        List<String> names=getNames();

        //组装消息
        String message = MessageUtils.getNames( MessageUtils.CODE_THREE,names);

        //发送给所有用户

        broadcastAllUsers(message);




    }

    /**
     * 接收消息
     *
     * @param message 前端发送过来的消息内容
     * @param session 当前会话对象
     */
    @OnMessage
    private void OnMessage(String message, Session session) throws IOException {
        //获取并解析
        Map<String, String> messagemap = JSON.parseObject(message, Map.class);
        String code = messagemap.get("code");
        String fromName = messagemap.get("fromName");
        String toName = messagemap.get("toName");
        String content = messagemap.get("content");



        //组装消息
        String messagecontent = MessageUtils.getContent(code,fromName, toName, content);
        if ("1".equals(code)) {
            //接收人为所有用户
            //发送给所有人
            broadcastAllUsers(messagecontent);

        }else {
            //给指定用户发送消息
            pushMessage(messagecontent,fromName,toName);

        }

    }
    @Override
    public void onClose(Session session,CloseReason closeReason){
        //用户减一
        subtractCount();

    }


    /**
     *
     * @param messagecontent  消息内容
     * @param fromName      发送人
     * @param toName      接收人
     */
        private void pushMessage(String messagecontent,String fromName,String toName){
            for (HttpSession httpSession1 : onLinUsers.keySet()) {
                //找到发送者，和接收者，然后给两者发送
                if (httpSession1.getAttribute("username").equals(fromName)||httpSession1.getAttribute("username").equals(toName))
                {
                    try {
                        onLinUsers.get(httpSession1).session.getBasicRemote().sendText(messagecontent);
                    } catch (IOException e) {
                     e.printStackTrace();
                    }
                }
            }



        }





        /**
         * 发送给所以用户消息
         * @param message 发送内容
         * @throws IOException
         */
        private void broadcastAllUsers (String message){
            for (HttpSession httpSession1 : onLinUsers.keySet()) {
                try {
                    onLinUsers.get(httpSession1).session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }

        /**
         * 获取当前登录的所以用户名
         * @return 返回所以在线用户名
         */
        private List<String> getNames () {

            List<String> list=new ArrayList<String>();
            if (onLinUsers.size() > 0) {
                for (HttpSession httpSession1 : onLinUsers.keySet()) {
                    String username = (String) httpSession1.getAttribute("username");
                   list.add(username);

                }
            }

            return list;

        }

    /**
     * 用户登录，用户加一
     */
    public void addCount() {
        onlineCount++;
    }

    /**
     * 用户退出，用户减一
     */
    public void subtractCount() {
        onlineCount--;
    }

    /**
     *
     * @return 当前用户数量
     */
    public int getOnlineCount() {
        return onlineCount;
    }


}

