//package com.lck.reverse.stu.rabbitmq.direct;
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
//public class DirectProducer {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//
//    @GetMapping("/direct")
//    public ResultMessage direct(){
//        String content="direct 路由模式模式 "+ new Date();
//
//        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_EXCHANGE,"direct.b.key",content);
//        log.info("direct 生产者生产了一个消息==> "+content);
//        return ResultMessage.getDefaultResultMessage(200,"rabbitmq路由模式成功发送了一个消息");
//    }
//}
