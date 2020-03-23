package com.mmall.common;

import com.mmall.exception.ParamException;
import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
        Object o, Exception ex) {

        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg =  "system error";

        //.json  .page
        //项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")) {
            JsonData result;
            if (ex instanceof PermissionException ||ex instanceof ParamException) {
                result = JsonData.fail(ex.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            } else {
                log.error("未知json异常   url: {}"  ,url,ex );
                result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
            //请求page页面  都使用.page 结尾
        } else if (url.endsWith(".page")) {
            log.error("未知页面异常  url: {}"  ,url,ex );
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());
        } else {
            log.error("未知异常  url: {}"  ,url,ex );
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }

        return mv;
    }
}
