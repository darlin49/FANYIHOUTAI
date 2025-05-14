package com.example.fanyihoutai.service;

import com.example.fanyihoutai.Word;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class YoudaoWordDefinitionService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public YoudaoWordDefinitionService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Word getWordDefinition(String word) {
        try {
            // 构建URL - 使用固定格式的API链接
            String url = String.format("https://dict.youdao.com/jsonapi_s?" +
                    "q=%s&" +
                    "doctype=json&" +
                    "jsonversion=4&" +
                    "le=en&" +
                    "t=0&" +
                    "client=web&" +
                    "sign=35376ae4129ab55d7dea33838065a5de&" +  // 使用固定sign
                    "keyfrom=webdict",
                    URLEncoder.encode(word, StandardCharsets.UTF_8));

            // 发送请求
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
                        String basicMeaning = trs.get(0).get("tran").asText();
                        meaning.append(basicMeaning);
                    }
                }
            }

            // 如果没有基本释义，尝试解析网络释义 (web_trans部分)、、、
            if (meaning.length() == 0 && root.has("web_trans")) {
                JsonNode webTrans = root.get("web_trans").get("web-translation");
                if (webTrans != null && webTrans.isArray() && webTrans.size() > 0) {
                    JsonNode trans = webTrans.get(0).get("trans");
                    if (trans != null && trans.isArray() && trans.size() > 0) {
                        String webMeaning = trans.get(0).get("value").asText();
                        meaning.append(webMeaning);
                    }
                }
            }

            word.setMean(meaning.toString());
            return word;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}