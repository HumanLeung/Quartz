package com.example.demo.timerservice;

import com.example.demo.info.TimerInfo;
import com.example.demo.util.TimerUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SchedulerService {
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private final Scheduler scheduler;

    @Autowired
   public SchedulerService(Scheduler scheduler){
       this.scheduler = scheduler;
   }

   @PostConstruct
   public void init(){
        try{
            scheduler.start();
            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
        }catch (SchedulerException e){
           e.printStackTrace();
        }
   }

    public <T extends Job> void schedule(final Class<T> jobClass, final TimerInfo info) {
        final JobDetail jobDetail = TimerUtils.buildJobDetail(jobClass, info);
        final Trigger trigger = TimerUtils.buildTrigger(jobClass, info);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
    public Boolean deleteTimer(final String timerId) {
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public List<Object> getAllRunningTimers() {
        try {
            return scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            final JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                            System.out.println(jobKey);
                            System.out.println(jobDetail);
                            System.out.println(jobDetail.getJobDataMap().get(jobKey.getName()));
                            return jobDetail.getJobDataMap().get(jobKey.getName());
                        } catch (final SchedulerException e) {
                            log.error(e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (final SchedulerException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public void updateTimer(final String timerId, final TimerInfo info) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                log.error("Failed to find timer with ID '{}'", timerId);
                return;
            }

            jobDetail.getJobDataMap().put(timerId, info);

            scheduler.addJob(jobDetail, true, true);
        } catch (final SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }

    public TimerInfo getRunningTimer(final String timerId) {
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null) {
                log.error("Failed to find timer with ID '{}'", timerId);
                return null;
            }
            return (TimerInfo) jobDetail.getJobDataMap().get(timerId);
        } catch (final SchedulerException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    public void pauseJob(final String timerId){
       try {
           final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
           if (jobDetail == null){
               log.error("Failed to find timer with ID '{}'",timerId);
           }else {
               scheduler.pauseJob(new JobKey(timerId));
           }
       }catch (final SchedulerException ignored){
       }
    }

    public void resumeJob(final String timerId){
        try {
            final JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            if (jobDetail == null){
                log.error("Failed to find timer with ID '{}'",timerId);
            }else {
                scheduler.resumeJob(new JobKey(timerId));
            }
        }catch (final SchedulerException ignored){
        }
    }

   @PreDestroy
    public void preDestroy(){
       try {
           scheduler.shutdown();
       } catch (SchedulerException e) {
           e.printStackTrace();
       }
   }
}
