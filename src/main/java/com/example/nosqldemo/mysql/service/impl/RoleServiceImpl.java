package com.example.nosqldemo.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.nosqldemo.mysql.entity.Role;
import com.example.nosqldemo.mysql.mapper.RoleMapper;
import com.example.nosqldemo.mysql.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public Role getRole(Integer id) {
        return getById(id);
    }
}
