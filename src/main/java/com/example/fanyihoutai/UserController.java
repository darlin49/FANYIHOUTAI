package com.example.fanyihoutai;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fanyihoutai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080") //

public class UserController {

    @Autowired
    private UserService userService;

    // 获取所有用户
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.list();
    }

    // 添加用户
    @PostMapping("/users")
    public boolean addUser(@RequestBody User user) {
        return userService.save(user);
    }

    // 更新用户
    @PutMapping("/users/{id}")
    public boolean updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return userService.updateById(user);
    }

    // 删除用户
    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    // 搜索用户
    @GetMapping("/users/search")
    public List<User> searchUsers(@RequestParam String keyword) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", keyword)
                .or()
                .like("nickname", keyword);
        return userService.list(queryWrapper);
    }
}