package com.example.demo.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class topicMessagesListener {
    @RabbitListener(queues="topic.messages")
    public void process(String str) {
        System.out.println("topic.messages:"+str);
    }
}
