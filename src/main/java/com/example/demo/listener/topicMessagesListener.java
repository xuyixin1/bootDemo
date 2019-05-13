package com.example.demo.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class topicMessagesListener {
    @RabbitListener(queues="topic.messages")
    public void process(Message message , Channel channel)throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("topic.messages:"+  new String(message.getBody()));
    }
}
