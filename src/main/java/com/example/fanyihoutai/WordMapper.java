package com.example.fanyihoutai;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper  // 确保添加了 @Mapper 注解
public interface WordMapper extends BaseMapper<Word> {
    // 可以添加自定义查询方法
}
