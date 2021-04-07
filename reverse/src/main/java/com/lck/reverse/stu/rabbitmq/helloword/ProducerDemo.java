//package com.lck.reverse.stu.rabbitmq.helloword;
//
//
//import com.lck.reverse.entity.respon.ResultMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RestController
//@Slf4j
//public class ProducerDemo {
//    @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    @GetMapping("/send")
//    public ResultMessage send(){
//        String content="hello rabbit mq"+ new Date();
//        //往"queueName"队列里面发送消息（先在mq的控制台创建一个queueName队列）
//        amqpTemplate.convertAndSend("queueName",content);
//        log.info("生产者生产了一个消息==> "+content);
//        return ResultMessage.getDefaultResultMessage(200,"rabbitmq发送了一个消息");
//    }
//}
