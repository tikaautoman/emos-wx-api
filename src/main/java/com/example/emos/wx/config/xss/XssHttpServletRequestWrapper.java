package com.example.emos.wx.config.xss;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;
    private HttpServletRequest request;
    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.request = request;
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
//        StringBuilder sb = new StringBuilder();
//        String line;
//        BufferedReader reader = request.getReader();
//        while ((line = reader.readLine())!=null){
//            sb.append(line);
//        }
//        String body = sb.toString();
//        System.out.println("body========"+body);
//        System.out.println("body length="+body.length());
//        this.body = body.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getParameter(String name){
        String value = super.getParameter(name);
        if (!StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name){
        String[] values = super.getParameterValues(name);
        if(values != null){
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if(!StrUtil.hasEmpty(value)){
                    value = HtmlUtil.filter(value);
                }
                values[i] = value;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap(){
        Map<String, String[]> parameters = super.getParameterMap();
        Map<String, String[]> map = new LinkedHashMap<>();
        if(parameters != null){
            for (String key :
                    parameters.keySet()) {
                String[] values = parameters.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if(!StrUtil.hasEmpty(value)){
                        value = HtmlUtil.filter(value);
                    }
                    values[i] = value;
                }
                map.put(key, values);
            }
        }
        return map;
    }

    @Override
    public String getHeader(String name){
        String value = super.getHeader(name);
        if (!StrUtil.hasEmpty(value)){
            value = HtmlUtil.filter(value);
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException{
//        InputStream in = super.getInputStream();
//        StringBuffer body = new StringBuffer();
//        InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
//        BufferedReader buffer = new BufferedReader(reader);
//        String line = buffer.readLine();
//        while (line != null){
//            body.append(line);
//            line = buffer.readLine();
//        }
//        buffer.close();
//        reader.close();
//        in.close();
//        System.out.println("body==================="+body.toString());
//        System.out.println(body.length);

        Map<String, String[]> param = request.getParameterMap();

        Map<String, Object> map = JSONUtil.parseObj(param);
        Map<String, Object> resultMap = new HashMap(map.size());
        for (String key : map.keySet()){
            Object val = map.get(key);
            if (map.get(key) instanceof String){
                resultMap.put(key, HtmlUtil.filter(val.toString()));
            }else {
                resultMap.put(key,val);
            }
        }
        String str = JSONUtil.toJsonStr(resultMap);
        final ByteArrayInputStream bain = new ByteArrayInputStream(str.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bain.read();
            }
        };
    }
}
