package com.cxb.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: cxb
 * @create: 2022-06-28 21:11
 */
public class Consumer1 {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.106.6");
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

//       预取值，设置信道容量大小，0为无穷大
        channel.basicQos(2);

//        autoack消息自动应答为否
        channel.basicConsume(QUEUE_NAME,false,
                ( consumerTag,  message) ->{
                    System.out.println("c1等待开始接收消息");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("接受到的消息为：" + new String(message.getBody(), "UTF-8"));
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                },(String consumerTag) ->{
                    System.out.println("消息消费失败");
                });
    }
}
