//package com.lck.reverse.stu.rabbitmq.helloword;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RabbitListener(queues = "queueName")//监听这个队列
//@Slf4j
//public class ConsumerDemo {
//    @RabbitHandler
//    public void process(String hello){
//        log.info("receiver.rabbitmq <===="+ hello);
//    }
//}
