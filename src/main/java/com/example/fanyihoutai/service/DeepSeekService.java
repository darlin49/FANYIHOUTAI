package com.example.fanyihoutai.service;

import com.example.fanyihoutai.config.DeepSeekConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DeepSeek API 服务类
 * 负责与 DeepSeek API 进行通信，发送请求并处理响应
 */
@Service
public class DeepSeekService {

    /**
     * 注入 DeepSeek 配置类，包含 API 密钥和 URL
     */
    @Autowired
    private DeepSeekConfig deepSeekConfig;

    /**
     * RestTemplate 实例，用于发送 HTTP 请求
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 查询单词的方法
     * 
     * @param word 要查询的单词
     * @param prompt 查询提示词，用于指导 AI 如何回答
     * @return AI 返回的解释文本
     * @throws RuntimeException 当 API 调用失败时抛出异常
     */
    public String queryWord(String word, String prompt) {
        // 设置请求头，包含 Content-Type 和认证信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(deepSeekConfig.getApiKey());

        // 构建消息列表
        List<Map<String, String>> messages = new ArrayList<>();
        
        // 添加系统角色消息，设置 AI 助手的行为
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "你是一个专业的英语词典助手，擅长提供准确的中文释义。请使用中文进行回答，注意使用规范的中文标点符号。");
        messages.add(systemMessage);
        
        // 添加用户消息，包含查询的单词和提示词
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt + " " + word);
        messages.add(userMessage);

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "deepseek-chat");  // 使用 deepseek-chat 模型
        requestBody.put("messages", messages);       // 设置对话消息
        requestBody.put("stream", false);           // 不使用流式响应
        
        // 添加 response_format 参数以确保 JSON 输出
        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");
        requestBody.put("response_format", responseFormat);

        // 创建 HTTP 请求实体
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            // 发送 POST 请求到 DeepSeek API
            ResponseEntity<Map> response = restTemplate.exchange(
                deepSeekConfig.getApiUrl(),
                HttpMethod.POST,
                request,
                Map.class
            );

            // 处理响应
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                System.out.println("API Response: " + responseBody); // 调试日志
                
                // 解析响应数据
                if (responseBody.containsKey("choices")) {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
                    if (!choices.isEmpty()) {
                        Map<String, Object> choice = choices.get(0);
                        Map<String, Object> messageResponse = (Map<String, Object>) choice.get("message");
                        return (String) messageResponse.get("content");
                    }
                }
                throw new RuntimeException("API响应格式不正确: " + responseBody);
            }
            throw new RuntimeException("API请求失败: " + response.getStatusCode());
        } catch (Exception e) {
            System.out.println("API Error: " + e.getMessage()); // 调试日志
            throw new RuntimeException("调用DeepSeek API失败: " + e.getMessage(), e);
        }
    }
} 