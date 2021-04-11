//package com.lck.reverse.stu.rabbitmq.direct.prac;
//
//import com.alibaba.fastjson.JSONObject;
//import com.lck.reverse.utils.websocket.OneWebSocket;
//import lombok.extern.slf4j.Slf4j;
//import org.codehaus.plexus.util.StringUtils;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.OnMessage;
//import javax.websocket.Session;
//
//@Component
//@Slf4j
//public class RemoteDirectConsumer {
//
//    @Autowired
//    private OneWebSocket oneWebSocket;
//
//    @RabbitListener(queues = RemoteDirectConfig.QUEUE_DIRECT_A)
//    @RabbitHandler
//    public void processtopicA(String msg) {
//        log.info("路由模式direct队列RBCustomerAskServer获取消息  <========: " + msg);
//        if(StringUtils.isEmpty(msg))
//            log.info("");
//        JSONObject jsonObject = JSONObject.parseObject(msg);
//        String customerId = jsonObject.getString("customerId");
//        log.info("发送消息给你===》"+customerId);
//        oneWebSocket.sendMessageById(customerId,"通过websoct发送rabiitmaq中的消息 "+msg);
//    }
//
//    /**
//     * 服务端发送消息给客户端
//     */
//    private void sendMessage(String message, Session toSession) {
//        try {
//            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
//            toSession.getBasicRemote().sendText(message);
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败：{}", e);
//        }
//    }
//    @RabbitListener(queues = RemoteDirectConfig.QUEUE_DIRECT_B)
//    @RabbitHandler
//    public void processtopicB(String msg) {
//        log.info("路由模式direct队列RBServerRespCustomer获取消息  <===========: " + msg);
//    }
//
//}
