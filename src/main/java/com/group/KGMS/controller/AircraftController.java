package com.group.KGMS.controller;

import com.github.pagehelper.PageInfo;
import com.group.KGMS.entity.T_aircraft;
import com.group.KGMS.entity.RuleForm;

//import com.group.KGMS.repository.AircraftRepository;
import com.group.KGMS.service.AircraftService;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {
//    @Autowired
//    private AircraftRepository aircraftRepository;
    @Autowired
    private AircraftService aircraftService;
    private  Process proc ;

    @GetMapping("/findAll/{page}/{size}")
    public PageInfo<T_aircraft> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){

        return aircraftService.findAllAircraft(page,size);
    }
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
    @GetMapping("/search")
    public PageInfo<T_aircraft> search(RuleForm ruleForm){
        return aircraftService.search(ruleForm);
    }
    @PostMapping("/save")
    public String save(@RequestBody T_aircraft aircraft){
        int result = aircraftService.save(aircraft);
        if(result ==1){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public T_aircraft findById(@PathVariable("id") Integer id){
        return aircraftService.findById(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody T_aircraft aircraft){
        int result = aircraftService.update(aircraft);
        if(result == 1){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        aircraftService.delete(id);
    }
}
