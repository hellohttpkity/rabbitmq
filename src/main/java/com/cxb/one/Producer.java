package com.cxb.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: cxb
 * @create: 2022-06-27 21:31
 */
public class Producer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
//        链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
//        设置链接的主机
        connectionFactory.setHost("192.168.106.6");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

//        获取链接
        Connection connection = connectionFactory.newConnection();
//          创建信道
        Channel channel = connection.createChannel();

//        声明一个交换机
//        channel.exchangeDeclare();
//        将交换机和一个队列绑定，用routeKey
//        channel.queueBind()
        /*
        生成一个队列
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
//        发送一个消息
            channel.basicPublish("", QUEUE_NAME, null, message
                    .getBytes());
            System.out.println("发送消息成功");
        }


    }
}
