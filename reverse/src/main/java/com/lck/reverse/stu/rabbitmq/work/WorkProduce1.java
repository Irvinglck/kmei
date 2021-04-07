//package com.lck.reverse.stu.rabbitmq.work;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RabbitListener(queues = "workPattern")
//@Slf4j
//public class WorkProduce1 {
//    @RabbitHandler
//    public void prcess(String msg){
//        log.info("workProduce1 receive <==== : "+msg);
//    }
//}
