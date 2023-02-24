package com.group.KGMS.controller;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

@RestController
@RequestMapping("/crawl")
public class CrawlController {
    private  Process proc ;
    @GetMapping ("/start")
    public String start(){
        String result =null;
        String line = "";
        try {
        	/*
			附加：
			String[] args1=new String[]{"/home/huan/anaconda2/bin/python","/home/huan/myfile/pythonfile/helloword.py"};
            Process pr=Runtime.getRuntime().exec(args1);
			String数组里的那一行很重要
			首先一定要设置好你所使用的python的位置，切记不要直接使用python，因为系统会默认使用自带的python，所以一定要设置好你所使用的python的位置，否则可能会出现意想不到的问题（比如说我使用的是anaconda中的python，而ubuntu系统会默认调用自带的python，而我自带的python中并没有numpy库，所以会造成相应的代码不会执行的问题，所以设置好python的位置是很重要的）。还有就是要设置好py文件的位置，使用绝对路径。在这里插入代码片

       还有就是可以看出，此方法可以满足我们python代码中调用第三方库的情况，简单实用。
			*/
//            proc = Runtime.getRuntime().exec("E:\\代码\\DataCleaning\\venv\\Scripts\\python E:\\代码\\DataCleaning\\DataCleaning.py"+" "+aircraft);
            String[] args1=new String[]{"E:\\Anaconda\\python.exe","E:\\代码\\FKFD\\militory_factory\\militory_factory\\main.py"};
            proc = Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((line = in.readLine()) != null) {
                return line;
            }
            in.close();
            proc.waitFor();
            proc.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return line;
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
