package com.example.nosqldemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@SpringBootTest
public class RabbitmqTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void SendTest() {
        String msg = "rabbitmq生成者发送失败和消费失败处理方案";
        try {
            // 针对网络原因导致连接断开，利用retryTemplate重连10次
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(5));
            rabbitTemplate.setRetryTemplate(retryTemplate);

            // 确认是否发到交换机，若没有则存缓存，用另外的线程重发，直接在里面用rabbitTemplate重发会抛出循环依赖错误
            rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                if (!ack) {
                    // 存缓存操作
                    System.out.println(msg + "发送失败:" + cause);
                }
            });

            // 确认是否发到队列，若没有则存缓存，然后检查exchange, routingKey配置，之后重发
            rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
                // 存缓存操作
                System.out.println(new String(message.getBody()) + "找不到队列，exchange为" + exchange + ",routingKey为" + routingKey);
            });

            rabbitTemplate.convertAndSend("exchangeTest", "routingKeyTest", msg);
        } catch (AmqpException e) {
            // 存缓存操作
            System.out.println(msg + "发送失败:原因重连10次都没连上。");
        }
    }
}
