//package com.lck.reverse.stu.rabbitmq.fanout;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FanoutConfig {
//    //创建队列A
//    @Bean
//    public Queue getFanoutA() {
//        return new Queue("fanoutA", true);
//    }
//
//    //创建队列B
//    @Bean
//    public Queue getFanoutB() {
//        return new Queue("fanoutB", true);
//    }
//
//    //创建一个fanoutExchange
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanoutExchange");
//    }
//
//    //队列A绑定交换机
//    //注意队列入参的名称getFanoutA要跟建立的队列方法名称相同，这样才知道fanoutA绑定了该交换机
//    //交换机的名称也是如此
//    @Bean
//    public Binding bingFanoutExchangeFanoutA(Queue getFanoutA, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(getFanoutA).to(fanoutExchange);
//    }
//    //队列B绑定交换机
//    @Bean
//    public Binding bingFanoutExchangeFanoutB(Queue getFanoutB, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(getFanoutB).to(fanoutExchange);
//    }
//
//}
