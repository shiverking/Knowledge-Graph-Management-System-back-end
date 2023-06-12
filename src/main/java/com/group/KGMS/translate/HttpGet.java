package com.group.KGMS.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpGet {
    public static String get(String url, Map<String, String> params) {
        // 构建GET请求的URL和参数
        StringBuilder urlBuilder = new StringBuilder(url);
        if (params != null) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        String requestUrl = urlBuilder.toString();

        // 发送GET请求并返回响应结果
        try {
            URL realUrl = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            conn.disconnect();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
