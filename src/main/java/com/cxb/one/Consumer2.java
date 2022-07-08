package com.cxb.one;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * @author: cxb
 * @create: 2022-06-28 21:11
 */
public class Consumer2 {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.106.6");
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//      开启信道的发布确认模式
        channel.confirmSelect();

//       预取值，设置信道容量大小，0为无穷大
        channel.basicQos(5);

//        HashMap<Object, Object> map = new HashMap<>();
//        Set<Map.Entry<Object, Object>> set = map.entrySet();
//        for (Map.Entry<Object, Object> entry : set) {
//            entry.getKey();
//            entry.getValue();
//        }
//        autoack消息自动应答为否
        channel.basicConsume(QUEUE_NAME,false,
                ( consumerTag,  message) ->{
                    System.out.println("c2等待开始接收消息");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("接受到的消息为：" + new java.lang.String(message.getBody(), "UTF-8"));
                    channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                },(java.lang.String consumerTag) ->{
                    System.out.println("消息消费失败");
                });
    }
}
