package com.example.nosqldemo.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {
    // 这里用的是RabbitTemplate发消息，也可以用AmqpTemplate，推荐使用RabbitTemplate。
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/hello")
    public String sendMQ5(){
        String msg = "rabbitmq生成者发送失败和消费失败处理方案：主干代码";
        try {
            // 针对网络原因导致连接断开，利用retryTemplate重连10次
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));
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

            rabbitTemplate.convertAndSend("myExchange1", "routingKey4", msg);
        } catch (AmqpException e) {
            // 存缓存操作
            System.out.println(msg + "发送失败:原因重连10次都没连上。");
        }

        return "success";
    }
}
