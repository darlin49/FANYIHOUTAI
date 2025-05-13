package com.example.fanyihoutai;

import com.example.fanyihoutai.service.WordService;
import com.example.fanyihoutai.service.WordDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "*")  // 添加这一行

public class WorldController {

    @Autowired
    private WordService wordService;

    @Autowired
    private WordDefinitionService wordDefinitionService;

    @PostMapping("/world")
    public Result adw(@RequestBody Word word) {
        try {
            String inputWord = word.getWord().trim();
            System.out.println("原始输入: [" + inputWord + "]");
            
            // 直接调用服务层方法处理
            List<Word> results = wordService.processAndQueryWords(inputWord);
            return Result.success(results);
            
        } catch (Exception e) {
            System.out.println("处理失败1: " + e.getMessage());
            return Result.fail(Result.DEFAULT_ERROR_CODE, "处理失败: " + e.getMessage());
        }
    }

    @GetMapping("/word/definition/{word}")
    public Result getWordDefinition(@PathVariable String word) {
        try {
            System.out.println("开始查询单词定义: [" + word + "]");
            // 使用 WordDefinitionService 的标准模板查询单词
            var definition = wordDefinitionService.getFormattedDefinition(word);
//            System.out.println("查询结果:\n" + definition.toString());
            return Result.success(definition);
        } catch (Exception e) {
            System.out.println("查询失败: " + e.getMessage());
            e.printStackTrace(); // 打印详细错误信息
            return Result.fail(Result.DEFAULT_ERROR_CODE, "查询失败: " + e.getMessage());
        }
    }
}




