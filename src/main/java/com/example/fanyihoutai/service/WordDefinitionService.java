package com.example.fanyihoutai.service;

import com.example.fanyihoutai.Word;
import com.example.fanyihoutai.model.WordDefinition;
import com.example.fanyihoutai.util.WordParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 单词释义服务类
 * 负责调用DeepSeek API获取单词释义并转换为结构化对象
 */
@Service
public class WordDefinitionService {
    
    // 注入DeepSeek服务
    @Autowired
    private DeepSeekService deepSeekService;
    
    /**
     *             [中文释义1];
            [中文释义2];
            [...]

            要求：
            - 单词单独一行
            - 每个词性单独列出（如存在多个词性）
            - 中文释义用分号分隔，最后用分号结尾
            - 只返回释义内容，不要额外解释
            - 使用中文标点符号


     * 标准提示词模板
     * 用于指导AI生成特定格式的单词释义
     */
    private static final String DEFINITION_PROMPT = """
            请以严谨的JSON格式返回单词解析数据，严格使用下方数据结构，不要使用markdown：
                  
                  {
                    "data": [
                      {
                        "id": null,
                        "word": "目标单词",
                        "mean": "词性：中文释义；[领域]特定领域释义；<注释>补充说明"
                      }
                    ]
                  }
                  
                  附加要求：
                  1. 词性必须使用英文（如：v.、n.、adv.等）
                  2. 多个释义之间用分号分隔
                  
                  4. 如是领域单词标注用方括号包裹（如：[医] [计算机]）
                  5. 保持null值原样输出
                  6. 所有标点符号使用中文标点
                  
                  请解析这个单词：%s
            """;
    
    /**
     * 使用标准提示词获取单词释义
     * @param word 要查询的单词
     * @return 单词对象
     */
    public Word getFormattedDefinition(String word) {
        // 使用标准提示词调用DeepSeek API
        String response = deepSeekService.queryWord(word, String.format(DEFINITION_PROMPT, word));
        // 打印API返回的原始响应
        System.out.println("DeepSeek API 返回内容：\n" + response);
        // 将返回的文本解析为Word对象
        return WordParseUtil.parseToWord(response);
    }
    
    /**
     * 使用自定义提示词获取单词释义
     * @param word 要查询的单词
     * @param customPrompt 自定义提示词
     * @return 单词对象
     */
    public Word getFormattedDefinitionWithCustomPrompt(String word, String customPrompt) {
        // 使用自定义提示词调用DeepSeek API
        String response = deepSeekService.queryWord(word, customPrompt);
        System.out.printf("qwe="+response);
        // 将返回的文本解析为Word对象
        return WordParseUtil.parseToWord(response);
    }
}