package com.example.fanyihoutai;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("translations")  // 确保表名正确
public class DanCi {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String word;
    private String translation;
    private Integer count;
    private LocalDateTime lasttime;
}