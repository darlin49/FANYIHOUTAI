package com.example.fanyihoutai.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.fanyihoutai.User;
import com.example.fanyihoutai.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    // 你可以在这里添加自定义的查询方法
}
