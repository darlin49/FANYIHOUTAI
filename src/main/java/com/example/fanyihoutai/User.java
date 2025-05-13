package com.example.fanyihoutai;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("users")
public class User {
    @TableId
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String role;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}