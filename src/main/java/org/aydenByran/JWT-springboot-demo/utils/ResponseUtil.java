package org.cold92.back.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 封装response将json数据传递到前台
 * @author admin
 */
public class ResponseUtil {

    public static void transmitMap(HttpServletResponse response, Map map) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        // 使用Springboot内置的jackson将Map转换成json数据形式
        String result = new ObjectMapper().writeValueAsString(map);
        // 响应数据
        response.getWriter().println(result);
    }
}
