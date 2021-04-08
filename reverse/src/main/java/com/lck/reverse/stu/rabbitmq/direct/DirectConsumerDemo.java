//package com.lck.reverse.stu.rabbitmq.direct;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class DirectConsumerDemo {
//
//    @RabbitListener(queues = "direct.A")
//    @RabbitHandler
//    public void processtopicA(String msg) {
//        log.info("路由模式direct队列Exchanges direct.A  <========: " + msg);
//    }
//
//    @RabbitListener(queues = "direct.B")
//    @RabbitHandler
//    public void processtopicB(String msg) {
//        log.info("路由模式direct队列Exchanges direct.B  <===========: " + msg);
//    }
//    @RabbitListener(queues = "direct.C")
//    @RabbitHandler
//    public void processtopicC(String msg) {
//        log.info("路由模式direct队列Exchanges direct.C  <===========: " + msg);
//    }
//}
