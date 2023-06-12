package com.group.KGMS;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.group.KGMS.entity.T_aircraft;
import com.group.KGMS.translate.TransApi;
import com.group.KGMS.entity.UnstructuredText;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Group11ApplicationTests {
    @Test
    public void getAllText(){
        String APP_ID = "20230523001687061"; //id
        String SECURITY_KEY = "tKzDwbkTFAoYWVgyr9Ir"; //通行码

        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String keyValue = api.getTransResult("hello", "auto", "zh"); //参数：需要翻译的内容、auto为自动识别语言，zh为中文

        Map<String,Object> map = (Map<String,Object>) JSON.parse(keyValue); //用于接收百度翻译返回的map
        System.out.println(map);
        String transResult = map.get("trans_result").toString(); //获取翻译结果对应的字符串
        JSONArray jsonArray = JSON.parseArray(transResult); //将字符串转换为JSONArray数组
        JSONObject obj = jsonArray.getJSONObject(0);
        String dstValue = obj.getString("dst");
        System.out.println(dstValue);

//        String dstValue = "";

    }



}
