package com.example.demo.util;

import lombok.Getter;

public class RabbitMQUtils {
    private static final String EXCHANGE = "our-exc";
    private static final String TYPE = "direct";
    private static final String ROUTING = "routing-key";
    private static final String QUEUE = "our-queue";

    public static String getEXCHANGE() {
        return EXCHANGE;
    }

    public static String getTYPE() {
        return TYPE;
    }

    public static String getRouting() {
        return ROUTING;
    }

    public static String getQUEUE() {
        return QUEUE;
    }
}
