package com.lck.reverse.stu.rabbitmq.work;


import com.lck.reverse.entity.respon.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class WorkProducerDemo {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/work")
    public ResultMessage work(){
        String content="work rabbit "+ new Date();
        //往"workPattern"队列里面发送消息（先在mq的控制台创建一个workPattern队列）
        rabbitTemplate.convertAndSend("workPattern",content);
        log.info("生产者生产了一个消息==> "+content);
        return ResultMessage.getDefaultResultMessage(200,"rabbitmq发送了一个消息");
    }
}
