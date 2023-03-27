package com.group.KGMS.controller;

import com.group.KGMS.entity.Record;
import com.group.KGMS.entity.RuleForm;
import com.group.KGMS.entity.T_crawler;
import com.group.KGMS.service.CrawlerService;
import com.group.KGMS.utils.JsonResult;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crawl")
public class CrawlController {
    private  Process proc ;

    @Autowired
    private CrawlerService crawlerService;

    //@GetMapping ("/list/{name}/{status}/{page}/{limit}")
    @GetMapping ("/list")
    public JsonResult findByCondition(String name, Integer status, Integer page, Integer limit) {
        return crawlerService.findByCondition(name, status, page, limit);
    }

    @GetMapping ("/findrecordbycid/{page}/{limit}/{cid}")
    public JsonResult findrecordbycid(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit,@PathVariable("cid") Integer cid) {
        return crawlerService.findrecordbycid(page,limit,cid);
    }

    @GetMapping ("/statistic")
    public Map<String, Long> statistic() {
        return crawlerService.statistic();
    }

    @PostMapping ("/addCrawler")
    public int addCrawler(@RequestBody T_crawler crawler) {
        return crawlerService.addCrawler(crawler);
    }

    @PostMapping ("/file")
    public void uploadCrawlerFile(@RequestParam MultipartFile file) {
        try {
            crawlerService.uploadCrawlerFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping ("/start/{cid}")
    public int start(@PathVariable("cid") Integer cid){
        String result =null;
        String line = "";
        int re=1;
        try {
            T_crawler t_crawler = crawlerService.findpathBycid(cid);
            String path=t_crawler.getPath();
            String spidername=t_crawler.getSpider_name();
            crawlerService.setcrawlstatusBycid(cid,1);
            String id =crawlerService.addrecord(cid);
//            proc = Runtime.getRuntime().exec("E:\\代码\\DataCleaning\\venv\\Scripts\\python E:\\代码\\DataCleaning\\DataCleaning.py"+" "+aircraft);
            String[] args1=new String[]{"E://Anaconda//python.exe","E:\\PycharmProjects\\FKFD专项\\Scenario\\main.py",path,spidername};
            proc = Runtime.getRuntime().exec(args1);
            re =proc.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((line = in.readLine()) != null) {
                System.out.println( line);
            }
            in.close();
            proc.destroy();
            if (re==0){
                crawlerService.setcrawlstatusBycid(cid,0);
                crawlerService.setrecordstatusBycid(id,0);
            }
            else {
                crawlerService.setcrawlstatusBycid(cid,-1);
                crawlerService.setrecordstatusBycid(id,-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return re;
    }
    public static void clearStream(InputStream stream) {
        String line = null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(stream));) {
            while ((line = in.readLine()) != null) {}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/stop")
    public int stop(){
        try {
            Field f = proc.getClass().getDeclaredField("handle");
            f.setAccessible(true);
            long handle = f.getLong(proc);
            Kernel32 kernel = Kernel32.INSTANCE;
            WinNT.HANDLE winntHandle = new WinNT.HANDLE();
            winntHandle.setPointer(Pointer.createConstant(handle));
            int pid = kernel.GetProcessId(winntHandle);
            System.out.println("进程id="+pid);
//            String[] args1=new String[]{"cmd.exe /c taskkill /t /pid"+pid};
            Runtime runtime = Runtime.getRuntime();
            // 打开任务管理器，exec方法调用后返回 Process 进程对象
            Process process = runtime.exec("cmd.exe /c taskkill /t /f /pid "+pid);
            clearStream(process.getInputStream());
            clearStream(process.getErrorStream());
            // 等待进程对象执行完成，并返回“退出值”，0 为正常，其他为异常
            int exitValue = process.waitFor();
            System.out.println("exitValue: " + exitValue);
            // 销毁process对象
            process.destroy();
            return exitValue;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return 5;
    }
}
