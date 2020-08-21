package com.gwwd.crowd.mvc.handler;


import com.github.pagehelper.PageInfo;
import com.gwwd.crowd.constant.CrowdConstant;
import com.gwwd.crowd.service.api.AdminService;
import com.gwwd.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    // 分页
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(

            @RequestParam(value = "keyword", defaultValue = "") String keyword,

            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,

            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,

            ModelMap modelMap
    ) {
        // 调用Service方法获取PageInfo 对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 将PageInfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);

        return "admin-page";
    }


    @RequestMapping("admin/do/logout.html")
    public String doLogout(HttpSession session) {

        // 强制session失效
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("admin/do/login.html")
    public String doLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    ) {

        // 调用service 方法执行登录检查
        // 这个方法如果能够返回admin对象说明登录成功,如果账号,密码不正确则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);

        // 将登录成功返回的admin对象存入session域中
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);

        return "redirect:/admin/to/main/page.html";
    }
}
