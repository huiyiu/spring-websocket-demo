package com.hyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * 测试 Controller 类
 *
 * @author mydlq
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

}
