package com.example.demo.util;

import com.example.demo.info.TimerInfo;
import org.quartz.*;

import java.sql.Time;
import java.util.Date;

public class TimerUtils {
    private TimerUtils(){}

    public static JobDetail buildJobDetail(final Class jobCLass, final Object info){
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobCLass.getSimpleName(),info);

        return JobBuilder
                .newJob(jobCLass).withIdentity(jobCLass.getSimpleName())
                .setJobData(jobDataMap)
                .build();
    }

    public static Trigger buildTrigger(final Class jobClass, final Object info) {
        SimpleScheduleBuilder builder = SimpleScheduleBuilder
                .simpleSchedule().withIntervalInMilliseconds(((TimerInfo)info).getRepeatIntervalMs());
        if (((TimerInfo)info).isRunForever()) {
            builder = builder.repeatForever();
        } else {
            builder = builder.withRepeatCount(((TimerInfo)info).getTotalFireCount() - 1);
        }

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date(System.currentTimeMillis() + ((TimerInfo)info).getInitialOffsetMs()))
                .build();
    }

    public static Trigger buildCronTrigger(final Class jobClass, final Object info){
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ? ");
        return TriggerBuilder.newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
