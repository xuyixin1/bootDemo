package com.example.demo.client;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQClient {
    @Autowired
    private AmqpTemplate template;

    public void send() {
        template.convertAndSend("exchange","topic.messages","hello,rabbit~");
        System.out.println("hello");
    }
}
