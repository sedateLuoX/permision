package com.mmall.common;

import com.mmall.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "beginTime";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String url = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info(" 请求开始  url ->{} , params ->{}" ,url, JsonMapper.objToString(parameterMap));
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME,start);
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        removeThreadLocalInfomation();

    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  {
        String url = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info(" 请求完成  url ->{} , params ->{}" ,url, JsonMapper.objToString(parameterMap));
        removeThreadLocalInfomation();
    }


    public void removeThreadLocalInfomation(){

        RequestHolder.remove();
    }
}
