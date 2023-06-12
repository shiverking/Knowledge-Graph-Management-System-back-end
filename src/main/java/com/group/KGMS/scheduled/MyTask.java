//package com.group.KGMS.scheduled;
//
//import com.group.KGMS.entity.T_crawler;
//import com.group.KGMS.service.CrawlerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.List;
//
//
//@Component
//    @EnableScheduling
//    public class MyTask implements SchedulingConfigurer {
//
//        @Autowired
//        CrawlerService crawlerService;
//        @Override
//        public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//            List<T_crawler> crawlers=crawlerService.findAllnopage();
//            for (int i=0 ; i < crawlers.size(); i++) {
//                int cid = crawlers.get(i).getCid();
//                String c = crawlers.get(i).getCron();
//                if(!(c.equals(""))) {
//                    scheduledTaskRegistrar.addTriggerTask(() -> process(cid),
//                            triggerContext -> {
//                                String cron = c;
//                                if (cron.isEmpty()) {
//                                    System.out.println("cron is null");
//                                }
//                                return new CronTrigger(cron).nextExecutionTime(triggerContext);
//                            });
//                }
//            }
//        }
//        private void process(int cid) {
//            String line = "";
//            int re=1;
//            Process proc =null;
//            try {
//                T_crawler t_crawler = crawlerService.findcrawlBycid(cid);
//                String path=t_crawler.getPath();
//                String spidername=t_crawler.getSpider_name();
//                crawlerService.setcrawlstatusBycid(cid,1);
//                String id =crawlerService.addrecord(cid);
////              proc = Runtime.getRuntime().exec("E:\\代码\\DataCleaning\\venv\\Scripts\\python E:\\代码\\DataCleaning\\DataCleaning.py"+" "+aircraft);
//                String[] args1=new String[]{"E://Anaconda//python.exe","E:\\PycharmProjects\\FKFD专项\\Scenario\\main.py",path,spidername};
//                proc = Runtime.getRuntime().exec(args1);
//                re =proc.waitFor();
//                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//                while ((line = in.readLine()) != null) {
//                    System.out.println( line);
//                }
//                in.close();
//                proc.destroy();
//                if (re==0){
//                    crawlerService.setcrawlstatusBycid(cid,0);
//                    crawlerService.setrecordstatusBycid(id,0);
//                }
//                else {
//                    crawlerService.setcrawlstatusBycid(cid,-1);
//                    crawlerService.setrecordstatusBycid(id,-1);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////        catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//            catch (Throwable e) {
//                e.printStackTrace();
//            }
//        }
//    }

