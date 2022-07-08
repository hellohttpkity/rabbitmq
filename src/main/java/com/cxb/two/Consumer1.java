package com.cxb.two;

import com.cxb.util.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Delivery;


import javax.xml.ws.FaultAction;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author: cxb
 * @create: 2022-06-29 19:45
 */
public class Consumer1 {

//    普通交换机
    private static final String NORMAL_EXCHANGE = "NORMAL_EXCHANGE";
//    死信交换机
    private static final String DEAD_EXCHANGE = "DEAD_EXCHANGE";
//普通队列
    private static final String NORMAL_QUEUE = "NORMAL_QUEUE";
//    死信队列
    private static final String DEAD_QUEUE = "DEAD_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtil.getChannel();

        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

//        放入一些参数
        HashMap<String, Object> arguments = new HashMap<>();
//设置消息过期时间为10s后，成为死信
        arguments.put("x-message-ttl", 10000);
//        正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
//      设置死信队列到死信交换机的路由
        arguments.put("x-dead-letter-routing-key", "lisi");
//        设置正常队列的最大值
        arguments.put("x-max-length", 6);

//        声明正常队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false,arguments );
//          声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
//      绑定交换机和队列
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        channel.basicConsume(NORMAL_QUEUE,false,
                (String consumerTag, Delivery message) ->{
                  String msg =   new String(message.getBody());
                    if (msg.equals("info5")) {
                        channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
                        System.out.println("被拒绝的消息为：" + msg);
                    }else {
                        channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                        System.out.println("接受到的消息为：" + new String(message.getBody()));
                    }

        },consumerTag -> {

                });

    }
}
