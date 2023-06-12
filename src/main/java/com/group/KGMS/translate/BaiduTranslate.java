package com.group.KGMS.translate;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public class BaiduTranslate {
    public String getresult(String query){
        String APP_ID = "20230523001687061"; //id
        String SECURITY_KEY = "tKzDwbkTFAoYWVgyr9Ir"; //通行码
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String keyValue = api.getTransResult(query, "auto", "zh"); //参数：需要翻译的内容、auto为自动识别语言，zh为中文
        String dstValue = null;
        try {
            Map<String, Object> map = (Map<String, Object>) JSON.parse(keyValue); //用于接收百度翻译返回的map
            String transResult = map.get("trans_result").toString(); //获取翻译结果对应的字符串
            JSONArray jsonArray = JSON.parseArray(transResult); //将字符串转换为JSONArray数组
            JSONObject obj = jsonArray.getJSONObject(0);
            dstValue = obj.getString("dst");
        }
        catch (Exception e){
        }
        return dstValue;
    }

}
