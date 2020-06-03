package com.example.nosqldemo.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.nosqldemo.annotation.NeedSetValueField;
import com.example.nosqldemo.mysql.entity.User;
import com.example.nosqldemo.mysql.mapper.UserMapper;
import com.example.nosqldemo.mysql.service.RoleService;
import com.example.nosqldemo.mysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @NeedSetValueField()
    @Override
    public List<User> getUserList() {
        return list();
    }

    @Override
    public List<User> getUserListAndRole() {
        List<User> userList = list();
        for (User item : userList) {
            item.setRoleName(roleService.getRole(item.getStatus()).getName());
        }
        return userList;
    }
}
