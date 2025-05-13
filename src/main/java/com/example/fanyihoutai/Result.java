package com.example.fanyihoutai;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * RESTful API 统一响应结构
 *
 * @param <T> 数据类型
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "message", "data", "timestamp"})
public class Result<T> {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 成功状态码
    public static final int SUCCESS_CODE = 200;
    // 默认错误状态码
    public static final int DEFAULT_ERROR_CODE = 500;

    /**
     * 状态码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳（格式：yyyy-MM-dd HH:mm:ss）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

    public Result() {
        this.timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setCode(SUCCESS_CODE)
                .setMessage("操作成功")
                .setData(data);
    }

    /**
     * 成功响应（无数据）
     */
    public static Result<Void> success() {
        return success(null);
    }

    /**
     * 业务异常响应
     */
    public static Result<Void> fail(int code, String message) {
        return new Result<Void>()
                .setCode(code)
                .setMessage(message);
    }

    /**
     * 系统错误响应
     */
    public static Result<Void> error() {
        return new Result<Void>()
                .setCode(DEFAULT_ERROR_CODE)
                .setMessage("系统繁忙，请稍后再试");
    }

    /**
     * 自定义错误响应（使用预定义的错误枚举）
     */
    public static Result<Void> error(ErrorCode errorCode) {
        return new Result<Void>()
                .setCode(errorCode.getCode())
                .setMessage(errorCode.getMessage());
    }
}

/**
 * 错误码枚举示例
 */
enum ErrorCode {
    USER_NOT_FOUND(1001, "用户不存在"),
    INVALID_PARAMETER(1002, "参数校验失败");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}