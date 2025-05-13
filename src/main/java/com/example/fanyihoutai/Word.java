package com.example.fanyihoutai;


import com.baomidou.mybatisplus.annotation.TableName;

@TableName("Words")  // 确保表名正确
public class Word {

    private Integer id;
    private String word;

    private String mean;


    // 无参构造 (JPA 要求)
    public Word() {
    }

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

               '}';
    }
}