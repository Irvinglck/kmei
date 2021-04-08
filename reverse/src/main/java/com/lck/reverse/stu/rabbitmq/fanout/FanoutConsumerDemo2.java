//package com.lck.reverse.stu.rabbitmq.fanout;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RabbitListener(queues = "fanoutB")
//@Slf4j
//public class FanoutConsumerDemo2 {
//
//    @RabbitHandler
//    public void process(String msg){
//        log.info("fanoutB 发布订阅模式接收消息 <===== : " + msg);
//    }
//}
