//package com.lck.reverse.stu.rabbitmq.fanout;
//
//import com.lck.reverse.entity.respon.ResultMessage;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RestController
//@Slf4j
//public class FanoutProducer {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//
//    @GetMapping("/fanout")
//    public ResultMessage fanout(){
//        String content="fanout 发布订阅广播模式 "+ new Date();
//
//        rabbitTemplate.convertAndSend("fanoutExchange","",content);
//        log.info("fanout 生产者生产了一个消息==> "+content);
//        return ResultMessage.getDefaultResultMessage(200,"rabbitmq发布订阅广播模式成功发送了一个消息");
//    }
//}
