//package com.lck.reverse.stu.rabbitmq.fanout;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RabbitListener(queues = "fanoutA")
//@Slf4j
//public class FanoutConsumerDemo1 {
//
//    @RabbitHandler
//    public void process(String msg) {
//        log.info("fanoutA 发布订阅模式接收消息 <===== : " + msg);
//    }
//}
