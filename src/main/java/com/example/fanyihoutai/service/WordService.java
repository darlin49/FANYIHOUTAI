package com.example.fanyihoutai.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.fanyihoutai.Word;
import com.example.fanyihoutai.WordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 单词服务类
 * 处理单词的数据库操作
 */
@Service
public class WordService extends ServiceImpl<WordMapper, Word> {
    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private WordDefinitionService wordDefinitionService;
    /**
     * 处理输入的单词字符串，将其拆分成单个单词并查询
     * @param inputWord 输入的单词字符串
     * @return 查询到的单词列表
     */
    public List<Word> processAndQueryWords(String inputWord) {
        List<String> words = new ArrayList<>();
        
        // 检查大写字母数量是否小于总字母数
        long totalLetters = inputWord.chars().filter(Character::isLetter).count();
        long upperCaseLetters = inputWord.chars().filter(Character::isUpperCase).count();
        
        if (upperCaseLetters < totalLetters) {
            // 如果大写字母数量小于总字母数，进行驼峰命名拆分
            Pattern pattern = Pattern.compile("[A-Z][a-z]*|[a-z]+|[A-Z]{2,}(?=[A-Z][a-z]|\\d|\\W|$)");
            java.util.regex.Matcher matcher = pattern.matcher(inputWord);
            
            while (matcher.find()) {
                String word = matcher.group().toLowerCase();
                System.out.printf("分开单词：%s%n", word);
                words.add(word);
            }
        } else {
            // 如果大写字母数等于总字母数，直接添加原始单词
            System.out.printf("使用原始单词：%s%n", inputWord.toLowerCase());
            words.add(inputWord.toLowerCase());
        }
        
        // 2. 查询数据库并返回结果列表
        List<Word> results = new ArrayList<>();

        for (String singleWord : words) {
            Word result = getWordByWordName(singleWord);

            if (result != null) {
                results.add(result);
            } else {
                var definition = wordDefinitionService.getFormattedDefinition(singleWord);
                results.add(definition);
            }
        }
        
        return results;
    }

    /**
     * 根据单词名称查询单词
     */
    public Word getWordByWordName(String wordName) {
        QueryWrapper<Word> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("word", wordName);
        queryWrapper.select("word","mean");
        //打印查询的单词信息
        System.out.println("查询的单词信息: " + wordMapper.selectOne(queryWrapper));
        return wordMapper.selectOne(queryWrapper);
    }
}