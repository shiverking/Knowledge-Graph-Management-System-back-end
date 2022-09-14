package com.group.KGMS.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.KGMS.entity.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应工具类
 */
public class ResponseUtil {

    public static void write(HttpServletResponse response, R r) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(),r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
