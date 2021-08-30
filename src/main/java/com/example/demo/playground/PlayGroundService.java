package com.example.demo.playground;

import com.example.demo.info.TimerInfo;
import com.example.demo.jobs.HelloWorldJob;
import com.example.demo.timerservice.SchedulerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayGroundService {
    private final SchedulerService schedulerService;

    public PlayGroundService(final SchedulerService schedulerService){
        this.schedulerService = schedulerService;
    }

    public void runHelloWorldJob() {
        final TimerInfo info = new TimerInfo();
        info.setTotalFireCount(5);
        info.setRemainingFireCount(info.getTotalFireCount());
        info.setRepeatIntervalMs(5000);
        info.setInitialOffsetMs(1000);
        info.setCallbackData("My callback data");

        schedulerService.schedule(HelloWorldJob.class, info);
    }
    public List<TimerInfo> getAllRunningTimers() {
        return schedulerService.getAllRunningTimers();
    }
    public TimerInfo getRunningTimer(final String timerId) {
        return schedulerService.getRunningTimer(timerId);
    }
    public Boolean deleteTimer(final String timerId) {
        return schedulerService.deleteTimer(timerId);
    }


}
