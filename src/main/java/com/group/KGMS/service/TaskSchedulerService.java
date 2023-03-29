package com.group.KGMS.service;
import com.group.KGMS.scheduled.TripleSearchTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
public class TaskSchedulerService {

    private final ThreadPoolTaskScheduler  taskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    public TaskSchedulerService() {
        taskScheduler = new ThreadPoolTaskScheduler();
//        this.taskScheduler.setPoolSize(8);
        this.taskScheduler.setThreadNamePrefix("scheduled-thread-");
        taskScheduler.initialize();
    }

    public void startTask(int interval) {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            String cronStr = "0 0/" +String.valueOf(interval)+" * * * ?";
            scheduledFuture = taskScheduler.schedule(new TripleSearchTask(), new CronTrigger(cronStr)); // 每隔1分钟执行一次
        }
    }

    public void stopTask() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
    }

    /**
     * 正在运行返回true,没有运行返回false
     */
    public boolean taskStatus() {
        if (scheduledFuture != null) {
            boolean isDone = scheduledFuture.isDone();
            boolean isCancelled = scheduledFuture.isCancelled();
            String res = "定时任务状态：" + (isDone ? "已完成" : isCancelled ? "已取消" : "正在执行");
            if(res.contains("正在执行")){
                return true;
            }
            return false;
        }
        return false;
    }
}

