package com.lck.reverse.stu.rabbitmq.config;

//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//配置多mq源
@Configuration
public class RabbitConfig {
//    @Value("${rabbitmq.queue.group}")
//    private String groupQueueName;
//    @Value("${rabbitmq.exchange}")
//    private String exchangeName;
//    @Value("${rabbitmq.addresses}")
//    private String address;
//    @Value("${rabbitmq.port}")
//    private Integer port;
//    @Value("${rabbitmq.username}")
//    private String userName;
//    @Value("${rabbitmq.pw}")
//    private String password;
//    @Value("${rabbitmq.virtual}")
//    private String virtualHost;
//
//    //创建连接工厂
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setPort(port);
//        connectionFactory.setAddresses(address);
//        connectionFactory.setUsername(userName);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(virtualHost);
//        connectionFactory.setConnectionTimeout(1000);
//        return connectionFactory;
//    }
//
//    //创建连接工厂的一个ampg管理
//    @Bean
//    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//
//    @Bean
//    Queue queue() {
//        return new Queue(groupQueueName, true);
//    }
//
//    //创建一个topic交换机，使用的是amqpAdmin来管理。
//    @Bean
//    TopicExchange exchange(AmqpAdmin amqpAdmin) {
//        TopicExchange exchange = new TopicExchange(exchangeName);
//        exchange.setAdminsThatShouldDeclare(amqpAdmin);
//        return exchange;
//    }
//
//    //创建一个模版，绑定的是connectionFactory这个工厂。
//    @Bean
//    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }
//
//
//    //创建第二个连接工厂
//    @Bean
//    public ConnectionFactory tempConnectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setPort(port);
//        connectionFactory.setAddresses(address);
//        connectionFactory.setUsername(userName);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost("temp");
//        connectionFactory.setConnectionTimeout(1000);
//        return connectionFactory;
//    }
//
//    //第二个管理
//    @Bean
//    public AmqpAdmin tempAdmin(ConnectionFactory tempConnectionFactory) {
//        return new RabbitAdmin(tempConnectionFactory);
//    }
//
//    //创建一个交换机，关联到tempAdmin这个上面
//    @Bean
//    TopicExchange tempExchange(AmqpAdmin tempAdmin) {
//        TopicExchange exchange = new TopicExchange("topic.A");
//        exchange.setAdminsThatShouldDeclare(tempAdmin);
//        return exchange;
//    }
//
//    //创建第二个template
//    @Bean
//    RabbitTemplate tempRabbitTemplate(ConnectionFactory tempConnectionFactory) {
//        return new RabbitTemplate(tempConnectionFactory);
//    }
//
//
//    //设置一个简单监听工厂。
//    @Bean
//    public SimpleRabbitListenerContainerFactory tempListenerContainerFactory(ConnectionFactory tempConnectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(tempConnectionFactory);
//        return factory;
//    }

}
