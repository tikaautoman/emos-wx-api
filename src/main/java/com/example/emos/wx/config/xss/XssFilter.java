package com.example.emos.wx.config.xss;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        System.out.println("request path="+((HttpServletRequest) servletRequest).getRequestURL());
        byte[] body;
        body = StreamUtils.copyToByteArray(servletRequest.getInputStream());
        filterChain.doFilter(xssRequest,servletResponse);
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
}
