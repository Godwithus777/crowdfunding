package com.gwwd.crowd.mvc.handler;


import com.github.pagehelper.PageInfo;
import com.gwwd.crowd.service.api.RoleService;
import com.gwwd.crowd.util.ResultEntity;
import com.gwwd.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    // 角色分页操作
    @ResponseBody
    @RequestMapping("role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "") String keyword
    ) {

        // 调用Service方法获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        // 封装到ResultEntity对象中返回（如果上面的操作抛出异常，交给异常映射机制处理）
        return ResultEntity.successWithData(pageInfo);
    }

    // 角色保存 新增
    @ResponseBody
    @RequestMapping("role/save.json")
    public ResultEntity<String> saveRole(Role role) {

        System.out.println("Role=" + role);
        roleService.saveRole(role);

        return ResultEntity.successWithoutData();
    }


}

