package com.gwwd.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.gwwd.crowd.mapper.RoleMapper;
import com.gwwd.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleIdList);
}
