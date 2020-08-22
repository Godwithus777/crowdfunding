package com.gwwd.crowd.mvc.interceptor;

import com.gwwd.crowd.Exception.AccessForbiddenException;
import com.gwwd.entity.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public LoginInterceptor() {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1.通过 request 对象获取 Session 对象
        HttpSession session = request.getSession();
        // 2.尝试从 Session 域中获取 Admin 对象
        Admin admin = (Admin) session.getAttribute("loginAdmin");
        // 3.判断 admin 对象是否为空
        if (admin == null) {
            // 4.抛出异常
            throw new AccessForbiddenException("请登录以后在访问!!!");
        } else {
            // 5.如果 Admin 对象不为 null，则返回 true 放行
            return true;
        }
    }
}
