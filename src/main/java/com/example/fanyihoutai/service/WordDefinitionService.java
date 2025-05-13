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
            请严格按照以下格式返回英文单词的词典释义（只返回下面三行格式的内容，不要其他任何内容）：
            
            [单词]
            [词性缩写].
            [中文释义1];[中文释义2];[...];

            要求：
            - 单词单独一行
            - 词性单独列出（如存在多个词性用用/划分）
            - 中文释义用分号分隔，最后用分号结尾
            - 只返回释义内容，不要额外解释
            - 使用英文标点符号
            
            现在请查询：%s
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
        // 将返回的文本解析为Word对象
        return WordParseUtil.parseToWord(response);
    }
}