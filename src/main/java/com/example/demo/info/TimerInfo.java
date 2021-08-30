package com.example.demo.info;

import lombok.Data;

import java.io.Serializable;

@Data
public class TimerInfo implements Serializable {
    private String jobName;
    private int totalFireCount;
    private int remainingFireCount;
    private boolean runForever;
    private long repeatIntervalMs;
    private long initialOffsetMs;
    private String callbackData;
}
