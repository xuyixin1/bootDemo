package com.example.demo.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class topicMessageListner {
    @RabbitListener(queues="topic.message")
    public void process(Message message , Channel channel)throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("topic.message:"+ new String(message.getBody()));
    }
}
