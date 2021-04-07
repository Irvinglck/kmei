package com.lck.reverse.stu.rabbitmq.helloword;

import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {
    @Bean
    public Queue queue(){
        return new Queue("queueName");
    }
}
