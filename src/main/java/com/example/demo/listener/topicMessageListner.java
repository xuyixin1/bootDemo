package com.example.demo.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class topicMessageListner {
    @RabbitListener(queues="topic.message")
    public void process(String str) {
        System.out.println("topic.message:"+str);
    }
}
