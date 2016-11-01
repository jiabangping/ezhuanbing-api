package com.ezhuanbing.api.service;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezhuanbing.api.dao.mybatis.model.mq.ImMessageQueueModel;

import net.sf.json.JSONObject;

@Service
public class MessageQueueService {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendMsg(ImMessageQueueModel imMessageQueueModel) {
    rabbitTemplate.convertAndSend("im_message_exchange","im_message_queue_key",
        JSONObject.fromObject(imMessageQueueModel));
    System.out.println("ezhuanbing has send data to rabbit mq");
  }
}
