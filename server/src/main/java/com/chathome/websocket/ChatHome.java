package com.chathome.websocket;

import com.chathome.util.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chathome.util.MessageUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//configurator获取当前用户的HttpSession
@ServerEndpoint(value = "/websocketchat",configurator =GetHttpSessionConfigurator.class)

public class ChatHome {
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
    @OnOpen
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
        String message = MessageUtils.getNames( MessageUtils.CODE_FOUR,names);

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
    public void OnMessage(String message, Session session) throws IOException {
        //获取并解析

        Map<String,String> map=new HashMap<String,String>();
        ObjectMapper objectMapper=new ObjectMapper();
        map=objectMapper.readValue(message,Map.class);

        String fromName = map.get("fromName");

        String toName = map.get("toName");

        String content = map.get("data");

        String code = map.get("code");




        //组装消息
        if ("1".equals(code)) {
            //接收人为所有用户
            //发送给所有人

            broadcastAllUsers(MessageUtils.getContent(code,fromName, toName, content));

        }else {
            //给指定用户发送消息
            pushMessage(fromName,toName,content);

        }

    }
   @OnClose
    public void OnClose(Session session,CloseReason closeReason){
        //用户减一
        subtractCount();

    }
    @OnError
    public void OnError(Session session,Throwable throwable){
        throwable.printStackTrace();
        System.out.println("服务器异常");

    }



    /**
     * @param content  消息内容
     * @param fromName      发送人
     * @param toName      接收人
     */ public void pushMessage(String fromName, String toName, String content){
            for (HttpSession httpSession1 : onLinUsers.keySet()) {


                //找到发送者，
                if (httpSession1.getAttribute("username").equals(fromName)) {
                    try {
                        onLinUsers.get(httpSession1).session.getBasicRemote().sendText(MessageUtils.getContent(MessageUtils.CODE_TWO,fromName, toName, content));
                    } catch (IOException e) {
                     e.printStackTrace();
                    }
                }
                //找出接收者
                if (httpSession1.getAttribute("username").equals(toName)){
                    try {
                        onLinUsers.get(httpSession1).session.getBasicRemote().sendText(MessageUtils.getContent(MessageUtils.CODE_THREE,fromName, toName, content));
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

