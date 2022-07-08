package com.cxb.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author: cxb
 * @create: 2022-06-28 20:59
 */
public class RabbitMQUtil {

    public static Channel getChannel() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.106.6");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
public static void main(String[] args) {
    String[] arr = {"a", "b", "c","d"};
    List<String> asList = Arrays.asList(arr);

    ArrayList<String> list = new ArrayList<>(asList);

//    for (int i = 0; i < list.size(); i++) {
//        list.remove(i);
//    }

    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
        String s = iterator.next();
        if (s.equals("a")) {
           iterator.remove();
        }
    }


    System.out.println(list);
}
}
