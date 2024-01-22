package com.panneer.springlearn.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component("SchedulerJob")
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
//@EnableAsync
public class SchedulerJob {

    @Scheduled(initialDelay = 5000, fixedDelay = 1000)
    void runSchedulerJobOne() throws InterruptedException {
        var current = LocalDateTime.now();
        var dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        log.info("current date in runSchedulerJobOne() is [{}]", current.format(dateTimeFormat));
        Thread.sleep(5000);
    }

    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    void runSchedulerJobTwo() throws InterruptedException {
        var current = LocalDateTime.now();
        var dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        log.info("current date in runSchedulerJobTwo() is [{}]", current.format(dateTimeFormat));
        Thread.sleep(5000);
    }

    //    @Async
    @Scheduled(initialDelay = 5000, fixedRate = 1000)
    void runSchedulerJobThree() throws InterruptedException {
        var current = LocalDateTime.now();
        var dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        log.info("current date in runSchedulerJobThree() is [{}]", current.format(dateTimeFormat));
        Thread.sleep(5000);
    }


    @Scheduled(cron = "*/5 * * * * *")
    void runSchedulerJobFour() throws InterruptedException {
        var current = LocalDateTime.now();
        var dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        log.info("current date in runSchedulerJobFour() is [{}]", current.format(dateTimeFormat));
        Thread.sleep(5000);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        var threadPookTaskScheduler = new ThreadPoolTaskScheduler();
        threadPookTaskScheduler.setThreadNamePrefix("sample-thread");
        threadPookTaskScheduler.setPoolSize(20);
        return threadPookTaskScheduler;
    }
}
