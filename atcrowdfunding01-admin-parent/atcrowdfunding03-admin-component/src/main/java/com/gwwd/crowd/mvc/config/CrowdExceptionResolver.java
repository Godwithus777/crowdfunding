package com.gwwd.crowd.mvc.config;


import com.google.gson.Gson;
import com.gwwd.crowd.Exception.AccessForbiddenException;
import com.gwwd.crowd.Exception.LoginFailedException;
import com.gwwd.crowd.util.CrowdUtil;
import com.gwwd.crowd.util.ResultEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;

//@ControllerAdvice表示当前类是一个基于注解的异常处理类
@ControllerAdvice
public class CrowdExceptionResolver {


    //@ExceptionHandler 将一个具体的异常类型和一个方法关联起来
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String viewName = "admin-login";

        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = AccessForbiddenException.class)
    public ModelAndView resolveAccessForbiddenException(
            AccessForbiddenException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "admin-login";

        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resovleMathExcption(
            ArithmeticException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String viewName = "system-error";

        return commonResolve(viewName, exception, request, response);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(
            NullPointerException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String viewName = "system-error";

        return commonResolve(viewName, exception, request, response);
    }

    private ModelAndView commonResolve(
            // 异常处理完成后要去的页面
            String viewName,

            //实际捕获到的异常类型
            Exception exception,

            // 当前请求对象
            HttpServletRequest request,
            // 当前响应对象
            HttpServletResponse response) throws IOException {

        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        if (judgeResult) {
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());

            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);


            response.getWriter().write(json);
            //{result: "FAILED"} 当注解的方法出现空指针异常时

            return null;
        }

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("exception", exception);

        modelAndView.setViewName(viewName);

        return modelAndView;


    }
}
