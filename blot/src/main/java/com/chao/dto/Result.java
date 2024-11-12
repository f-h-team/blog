package com.chao.dto;

import lombok.Data;

@Data
public class Result {
    private String code;
    private String msg;
    private Object data;
    public static Result success(Object data) {
        return Result.success(data,"操作成功");
    }
    public static Result success() {
        return Result.success(null,"操作成功");
    }
    public static Result success(Object data,String msg) {
        Result result = new Result();
        result.setCode("0");
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static Result error(String msg) {
        return Result.error(null,msg);
    }
    public static Result error(Object data,String msg) {
        Result result = new Result();
        result.setCode("-1");
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
}
