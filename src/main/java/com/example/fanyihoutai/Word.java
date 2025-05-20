package com.example.fanyihoutai;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;


@TableName("Words")  // 确保表名正确
public class Word {

    //id自增


    private Integer id;

    private String word;

    private String mean;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    // getter & setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }





    public String getMean() {
        return mean;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }






    // toString()（可选）
    @Override
    public String toString() {
        return "Word{" +
               "id=" + id +
               ", word='" + word + '\'' +
               ", mean='" + mean + '\'' +
                   ", createTime=" + createTime +
               '}';
    }
}