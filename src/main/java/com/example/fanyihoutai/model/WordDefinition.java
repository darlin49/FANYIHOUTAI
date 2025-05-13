package com.example.fanyihoutai.model;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

/**
 * 单词定义实体类，用于存储单词的完整释义信息
 */
@Data
public class WordDefinition {
    // 存储单词本身
    private String word;
    // 存储该单词的所有词性及其对应的释义
    private List<PartOfSpeech> definitions;

    /**
     * 构造函数
     * @param word 要创建定义的单词
     */
    public WordDefinition(String word) {
        this.word = word;
        this.definitions = new ArrayList<>();
    }

    /**
     * 内部类：表示单词的某一个词性及其所有释义
     */
    @Data
    public static class PartOfSpeech {
        // 词性缩写，如 n., v., adj. 等
        private String type;
        // 该词性下的所有中文释义
        private List<String> meanings;

        /**
         * 构造函数
         * @param type 词性缩写
         */
        public PartOfSpeech(String type) {
            this.type = type;
            this.meanings = new ArrayList<>();
        }

        /**
         * 将词性及其释义转换为格式化的字符串
         * 格式：
         * 词性.
         * 释义1;
         * 释义2;
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(type).append(".\n");
            for (String meaning : meanings) {
                sb.append(meaning).append(";\n");
            }
            return sb.toString();
        }
    }

    /**
     * 将整个单词定义转换为格式化的字符串
     * 格式：
     * 单词
     * 词性1.
     * 释义1;
     * 释义2;
     * 词性2.
     * 释义1;
     * ...
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append("\n");
        for (PartOfSpeech pos : definitions) {
            sb.append(pos.toString());
        }
        return sb.toString();
    }

    /**
     * 将DeepSeek API返回的文本解析为WordDefinition对象
     * @param text DeepSeek API返回的文本
     * @return 解析后的WordDefinition对象
     * @throws IllegalArgumentException 如果文本格式不正确
     */
    public static WordDefinition parseFromText(String text) {
        try {
            // 按行分割文本
            String[] lines = text.split("\n");
            if (lines.length < 2) {
                throw new IllegalArgumentException("Invalid definition format");
            }

            // 创建WordDefinition对象，使用第一行作为单词
            WordDefinition wordDef = new WordDefinition(lines[0].trim());
            PartOfSpeech currentPos = null;

            // 遍历每一行，解析词性和释义
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i].trim();
                if (line.isEmpty()) continue;

                if (line.endsWith(".")) {
                    // 以点号结尾的行是词性行
                    currentPos = new PartOfSpeech(line.substring(0, line.length() - 1));
                    wordDef.getDefinitions().add(currentPos);
                } else if (currentPos != null && line.endsWith(";")) {
                    // 以分号结尾的行是释义行
                    String meaning = line.substring(0, line.length() - 1).trim();
                    currentPos.getMeanings().add(meaning);
                }
            }

            return wordDef;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse word definition: " + e.getMessage());
        }
    }
} 