package com.example.demo.rabbitmq;

import com.example.demo.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.awt.geom.AreaOp;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


@Component
public class MQConsumer {
    private static final Logger logger = LoggerFactory.getLogger(MQConsumer.class);

    @PostConstruct
    private void init(){
        listenMessage();
    }

      private void listenMessage() {
          try{
              final ConnectionFactory factory = new ConnectionFactory();
              final Connection connection = factory.newConnection();
              final Channel channel = connection.createChannel();
              channel.queueDeclare(RabbitMQUtils.getQUEUE(),false,false,false,null);
              channel.queueBind(RabbitMQUtils.getQUEUE(),RabbitMQUtils.getEXCHANGE(),RabbitMQUtils.getRouting());
              channel.basicConsume(RabbitMQUtils.getQUEUE(),true,
                      ((consumerTag, message) -> {logger.info("Got message '{}'",
                              new String(message.getBody(), StandardCharsets.UTF_8));})
                      ,(consumerTag,sig) -> {logger.error(sig.getMessage());});
          }catch (final Exception e){
              logger.error(e.getMessage(),e);
          }
      }
}
