//package com.lck.reverse.stu.rabbitmq.direct.prac;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class RemoteDirectConfig {
//    public static final String DIRECT_EXCHANGE="RemoteBankDirectExchange";
//    public static final String QUEUE_DIRECT_A="RBCustomerAskServer";
//    public static final String QUEUE_DIRECT_B="RBServerRespCustomer";
//
//    //创建一个direct交换机
//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange(DIRECT_EXCHANGE);
//    }
//    //创建队列A
//    @Bean
//    public Queue queueDirectA(){
//        return new Queue(QUEUE_DIRECT_A);
//    }
//    //创建队列B
//    @Bean
//    public Queue queueDirectB(){
//        return new Queue(QUEUE_DIRECT_B);
//    }
//
//    //队列绑定交换机
//    //将direct.A队列绑定到DirectExchage交换机中，使用direct.a.key作为路由规则
//    @Bean
//    public Binding bingDirectExchageDirectA(Queue queueDirectA, DirectExchange directExchange){
//        //队列direct.A绑定到directExchange交换机并且指定了路由规则（direct.a.key）
//        return BindingBuilder.bind(queueDirectA).to(directExchange).with("direct.customer.key");
//    }
//
//    @Bean
//    public Binding bingDirectExchageDirectB(Queue queueDirectB, DirectExchange directExchange){
//        return BindingBuilder.bind(queueDirectB).to(directExchange).with("direct.server.key");
//    }
//
//}
