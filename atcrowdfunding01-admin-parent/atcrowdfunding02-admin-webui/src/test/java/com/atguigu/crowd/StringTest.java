package com.atguigu.crowd;

import com.github.pagehelper.PageInfo;
import com.gwwd.crowd.service.api.AdminService;
import com.gwwd.crowd.util.CrowdUtil;
import com.gwwd.entity.Admin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StringTest {

    @Autowired
    private AdminService adminService;

    @Test
    public void testMd5(){
        String source = "123123";
        String encoded = CrowdUtil.md5(source);
        System.out.println(encoded);
    }

    @Test
    public void testpageInfo() {
        // 调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo("", 1, 5);
        System.out.println(pageInfo);
        System.out.println(pageInfo.getList());

    }
}
