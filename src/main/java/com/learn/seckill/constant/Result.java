package com.learn.seckill.constant;

public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(T data) {
        this.data = data;
    }
    public Result(CodeMsg data) {
        if(data != null){
            this.code = data.getCode();
            this.data = (T) data.getMsg();
        }
    }
    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> error(CodeMsg data){
        return new Result<T>(data);
    }
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
