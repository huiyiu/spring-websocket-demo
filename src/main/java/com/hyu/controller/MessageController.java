package com.hyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * 测试 Controller 类
 *
 * @author wanghy
 */
@Controller
public class MessageController {

    /** 消息发送工具对象 */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /** 广播发送消息，将消息发送到指定的目标地址 */
    @MessageMapping("/test")
    public void sendTopicMessage(String msg) {
        // 将消息发送到 WebSocket 配置类中配置的代理中（/topic）进行消息转发
        simpMessageSendingOperations.convertAndSend("/topic/test", msg);
    }

    /**
     *  @SendTo("/topic/test2") === simpMessageSendingOperations.convertAndSend("/topic/test2", msg)
     * @param msg
     * @return
     */
    @MessageMapping("/test2")
    @SendTo("/topic/test2")
    public String sendTopicMessageWithSendTo(String msg) {
         return msg;
    }


    /**
     *  消息地址为 /topic/test3,前端发送消息地址为/app/test3
     *  订阅地址为 /user/queue/test3
     * @param msg
     * @return
     */
    @MessageMapping("/test3")
    @SendToUser(value="/queue/test3",broadcast = false)
    public String sendTopicMessageWithSendToUser(String msg, Principal principal) {
        return msg + principal.getName();
    }

}
