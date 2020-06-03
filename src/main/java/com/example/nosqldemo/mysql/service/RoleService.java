package com.example.nosqldemo.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.nosqldemo.mysql.entity.Role;

public interface RoleService extends IService<Role> {
    Role getRole(Integer id);
}
