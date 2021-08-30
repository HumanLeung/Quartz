package com.example.demo.rabbitmq;

import com.example.demo.util.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Component
public class MQSender {
    private static final Logger logger = LoggerFactory.getLogger(MQSender.class);

    @PostConstruct
    private void init(){
    final Scanner scanner = new Scanner(System.in);
        asForMessage(scanner);
    }

    private void sendMessage(final String message){
        final ConnectionFactory factory = new ConnectionFactory();

        try (final Connection connection = factory.newConnection()){
             final Channel channel = connection.createChannel();
             channel.exchangeDeclare(RabbitMQUtils.getEXCHANGE(),RabbitMQUtils.getTYPE());
             channel.basicPublish(RabbitMQUtils.getEXCHANGE(),RabbitMQUtils.getRouting(),false,null,message.getBytes());
        }catch (TimeoutException | IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
    private void asForMessage(final Scanner scanner){
        logger.info("Enter your message: ");
        final String message = scanner.nextLine();
        sendMessage(message);
        asForMessage(scanner);
    }
}
