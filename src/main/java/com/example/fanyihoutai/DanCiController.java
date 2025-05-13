package com.example.fanyihoutai;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fanyihoutai.service.DanCiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin  // 添加跨域支持
public class DanCiController {

    @Autowired
    private DanCiService userService;

    // 查询所有单词
    @GetMapping("/translations")
    public List<DanCi> getAllWords() {
        return userService.list();
    }

    // 根据单词查询
    @GetMapping("/user")
    public List<DanCi> getWordByName(@RequestParam String name) {
        QueryWrapper<DanCi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word", name);
        return userService.list(queryWrapper);
    }

    // 添加新单词
    @PostMapping("/translations")
    public DanCi addWord(@RequestBody DanCi word) {
        userService.save(word);
        return word;  // MyBatis-Plus 会自动填充生成的ID
    }

    // 更新单词
    @PutMapping("/translations/{id}")
    public boolean updateWord(@PathVariable Integer id, @RequestBody DanCi word) {
        word.setId(id);
        return userService.updateById(word);
    }

    // 删除单词
    @DeleteMapping("/translations/{id}")
    public boolean deleteWord(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @PostMapping("/api")
    public DanCi ad(@RequestBody String word) {

        System.out.printf(word);
        return null;

    }
    @GetMapping("/api")
    public DanCi getWordByNqame(@RequestParam String name) {
        System.out.printf(name);
        return null;
    }

}