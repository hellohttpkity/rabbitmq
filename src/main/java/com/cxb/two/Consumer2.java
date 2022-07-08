package com.cxb.two;

import com.cxb.util.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author: cxb
 * @create: 2022-06-29 19:45
 */
public class Consumer2 {


//    死信队列
    private static final String DEAD_QUEUE = "DEAD_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();



        channel.basicConsume(DEAD_QUEUE,true,
                (String consumerTag, Delivery message) ->{
                    System.out.println("死信接受到的消息为：" + new String(message.getBody()));
                },consumerTag -> {

                });
    }
}
