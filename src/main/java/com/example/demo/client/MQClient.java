package com.example.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MQClient implements  RabbitTemplate.ReturnCallback {
    private static final Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
    @Autowired
    private RabbitTemplate template;

    public void send() {
        CorrelationData data = new CorrelationData ();
        data.setId(UUID.randomUUID().toString());
        template.setReturnCallback(this);
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("消息发送失败" + cause + correlationData.toString());
            } else {
                log.debug("消息发送成功 " + correlationData.toString() );
            }
        });
        template.convertAndSend("exchange","topic.messages","hello,rabbit~", data);
        log.info("hello,rabbit~");
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("messagefial " + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }

    /*@Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
        log.debug("消息发送到exchange成功" + correlationData.getReturnedMessage());
    }else{
        log.error("发送消息失败" + correlationData.getReturnedMessage() + cause);
    }}*/

}
