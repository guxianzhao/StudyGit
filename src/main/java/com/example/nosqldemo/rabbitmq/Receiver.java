package com.example.nosqldemo.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    /**
     * basicNack(long deliveryTag, boolean multiple, boolean requeue)
     * deliveryTag: 每条消息在mq内部的id,
     * multiple: 是否批量(true：将一次性拒绝所有小于deliveryTag的消息)；
     * requeue: 是否重新入队
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "rabbitTest"),
                    exchange = @Exchange(value = "exchangeTest"),
                    key = "routingKeyTest"
            ))
    public void process7(Message message,Channel channel) throws Exception {
        // 模拟消费者代码异常，这种情况必须在catch块设置重试次数（也可以在配置文件中全局设置重试次数，当然百度的方案都不行，所以我没成功过），防止死循环
        // catch块中重试可用redis的自增来做计数器，从而控制重试次数
        try {
            int i = 1/0;
        } catch (Exception e) {
            System.out.println("queue:" +  new String(message.getBody()));
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            // 达到重试次数后用这行代码返回ack，并将消息存缓存
            // channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
