//package com.beyond.board.post.service;
//
//
//import org.springframework.batch.core.JobExecutionException;
//import org.springframework.batch.core.JobParameter;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//// batch 을 돌리기 위한 스케쥴러 정의
//public class PostBatchScheduler {
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private PostJobConfiguration postJobConfiguration;
//
//
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void batchSchedule() {
//
//        Map<String, JobParameter> configMap = new HashMap<>();
//
//        configMap.put("time", new JobParameter(System.currentTimeMillis()));
//        JobParameters jobParameters = new JobParameters(configMap);
//
//        try {
//            jobLauncher.run(postJobConfiguration.excuteJob(), jobParameters);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
