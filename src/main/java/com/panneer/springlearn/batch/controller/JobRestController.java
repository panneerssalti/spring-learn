package com.panneer.springlearn.batch.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@Slf4j
@RestController
@AllArgsConstructor
public class JobRestController {

    private JobLauncher jobLauncher;
    private Job job;

    @GetMapping("/runJob")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        var jobExe = jobLauncher.run(job, new JobParametersBuilder().addDate("timestamp", Calendar.getInstance().getTime()).toJobParameters());
        while (jobExe.isRunning()) {
            log.info("Job started...............................");
        }
        return jobExe.getStatus();
    }
}
