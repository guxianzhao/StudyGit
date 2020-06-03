package com.example.nosqldemo.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.nosqldemo.mysql.entity.User;

import java.util.List;


public interface UserService extends IService<User> {

    List<User> getUserList();

    List<User> getUserListAndRole();
}
