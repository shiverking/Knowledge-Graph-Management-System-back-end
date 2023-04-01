package com.group.KGMS.scheduled;

import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
    @EnableScheduling
    public class MyTask implements SchedulingConfigurer {

        @Autowired
        CrawlerService crawlerService;
        @Override
        public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
            List<T_crawler> crawlers=crawlerService.findAllnopage();
            for (int i=0 ; i < crawlers.size(); i++) {
                int cid = crawlers.get(i).getCid();
                String path = crawlers.get(i).getPath();
                scheduledTaskRegistrar.addTriggerTask(() -> process(cid,path),
                        triggerContext -> {
                            String cron = "0/60 * * * * ? ";
                            if (cron.isEmpty()) {
                                System.out.println("cron is null");
                            }
                            return new CronTrigger(cron).nextExecutionTime(triggerContext);
                        });
            }
        }
        private void process(int i,String path) {
            System.out.println("基于接口定时任务"+i+path);
        }
    }

