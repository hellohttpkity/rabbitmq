package com.cxb.two;

import com.cxb.util.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: cxb
 * @create: 2022-06-29 19:42
 */
public class Producer {
    //    普通交换机
    private static final String NORMAL_EXCHANGE = "NORMAL_EXCHANGE";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtil.getChannel();
//           设置消息的过期时间
//        AMQP.BasicProperties props = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "info" + i;
//            Thread.sleep(1000);

            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes(StandardCharsets.UTF_8));
        }
    }
}
