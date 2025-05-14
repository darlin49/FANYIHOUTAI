package com.example.fanyihoutai.util;

import com.example.fanyihoutai.Word;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 单词解析工具类
 * 用于将 DeepSeek API 返回的文本转换为 Word 对象
 */
public class WordParseUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 将 DeepSeek 返回的 JSON 文本解析为 Word 对象
     *
     * @param jsonText DeepSeek返回的JSON文本
     * @return 解析后的Word对象
     */
    public static Word parseToWord(String jsonText) {
        try {
            // 解析 JSON 文本
            JsonNode rootNode = objectMapper.readTree(jsonText);
            JsonNode dataArray = rootNode.get("data");
            if (dataArray == null || !dataArray.isArray() || dataArray.size() == 0) {
                throw new IllegalArgumentException("Invalid JSON format: missing or empty data array");
            }

            // 获取第一个数据对象
            JsonNode wordData = dataArray.get(0);
            
            Word word = new Word();
            word.setWord(wordData.get("word").asText());
            word.setMean(wordData.get("mean").asText());
            
            return word;
            //插入
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }
} 