package com.example.fanyihoutai.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.fanyihoutai.Word;
import com.example.fanyihoutai.WordMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoudaoWordDefinitionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final WordMapper wordMapper;

    public YoudaoWordDefinitionService(WordMapper wordMapper) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.wordMapper = wordMapper;
    }

    public Word getWordDefinition(String word) {
        try {
            // 首先检查数据库中是否已存在
            QueryWrapper<Word> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("word", word);
            Word existingWord = wordMapper.selectOne(queryWrapper);
            
            if (existingWord != null) {
                System.out.println("单词已存在于数据库中: " + word);
                return existingWord;
            }

            // 如果不存在，则调用有道API
            String url = String.format("https://dict.youdao.com/jsonapi_s?" +
                    "q=%s&" +
                    "doctype=json&" +
                    "jsonversion=4&" +
                    "le=en&" +
                    "t=0&" +
                    "client=web&" +
                    "sign=35376ae4129ab55d7dea33838065a5de&" +
                    "keyfrom=webdict",
                    URLEncoder.encode(word, StandardCharsets.UTF_8));

            String response = restTemplate.getForObject(url, String.class);
            return parseResponse(response, word);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Word parseResponse(String response, String originalWord) {
        try {
            JsonNode root = objectMapper.readTree(response);
            Word word = new Word();
            word.setWord(originalWord);

            StringBuilder meaning = new StringBuilder();

            // 解析基本释义 (ec部分)
            if (root.has("ec") && root.get("ec").has("word")) {
                JsonNode wordInfo = root.get("ec").get("word");
                if (wordInfo.has("trs")) {
                    JsonNode trs = wordInfo.get("trs");
                    if (trs.isArray() && trs.size() > 0) {
                        // 遍历所有释义
                        for (int i = 0; i < trs.size(); i++) {
                            JsonNode tr = trs.get(i);
                            String pos = tr.has("pos") ? tr.get("pos").asText() : "";
                            String tran = tr.get("tran").asText();
                            
                            // 添加词性和释义，如果有词性就加上词性
                            if (!pos.isEmpty()) {
                                meaning.append(pos).append("");
                            }
                            meaning.append(tran);
                            
                            // 如果不是最后一个释义，添加分隔符
                            if (i < trs.size() - 1) {
                                meaning.append(" ; ");
                            }
                        }

                    }
                }
            }
            

            // 如果没有基本释义，尝试解析网络释义 (web_trans部分)
            if (meaning.length() == 0 && root.has("web_trans")) {
                JsonNode webTrans = root.get("web_trans").get("web-translation");
                if (webTrans != null && webTrans.isArray() && webTrans.size() > 0) {
                    JsonNode trans = webTrans.get(0).get("trans");
                    if (trans != null && trans.isArray() && trans.size() > 0) {
                        String webMeaning = trans.get(0).get("value").asText();
                        meaning.append("网络释义: ").append(webMeaning);
                    }
                }
            }

            // 设置含义
            word.setMean(meaning.toString());
            
            // 只有在成功获取到含义时才保存到数据库
            if (meaning.length() > 0) {
                try {
                    System.out.println("准备插入新单词: " + originalWord);

                    wordMapper.insert(word);
                    System.out.println("成功插入单词: " + originalWord);
                } catch (Exception e) {
                    System.err.println("插入单词失败: " + originalWord);
                    e.printStackTrace();
                }
                return word;
            } else {
                System.out.println("未获取到单词释义，不保存到数据库: " + originalWord);
                return null;
            }

        } catch (Exception e) {
            System.err.println("解析响应失败: " + originalWord);
            e.printStackTrace();
            return null;
        }
    }
}