package com.ezhuanbing.api.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Configuration
@EnableRabbit
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix="rabbitmq")
public class RabbitmqConfig {

  private String queque_name;
  private String exchange_name;
  private String queque_exchange_bind_name;
  
  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
      return new RabbitAdmin(connectionFactory);
  }
  
  @Bean
  public Queue imQueue(RabbitAdmin rabbitAdmin) {
      Queue queue = new Queue(queque_name, true);
      rabbitAdmin.declareQueue(queue);
      return queue;
  }
  
  @Bean
  public DirectExchange exchage(RabbitAdmin rabbitAdmin){
    DirectExchange exchange = new DirectExchange(exchange_name,true,false);
    rabbitAdmin.declareExchange(exchange);
    return exchange;
  }
  
  @Bean
  public Binding bindingExchageImQueue(Queue imQueue, DirectExchange exchage, RabbitAdmin rabbitAdmin){
    Binding binding = BindingBuilder.bind(imQueue).to(exchage).with(queque_exchange_bind_name);
    rabbitAdmin.declareBinding(binding);
    return binding;
  }
  
  @Bean
  public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
      RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
      rabbitMessagingTemplate.setMessageConverter(jackson2Converter());
      rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
      return rabbitMessagingTemplate;
  }
  
  @Bean
  public MappingJackson2MessageConverter jackson2Converter() {
      MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
      return converter;
  }
}
