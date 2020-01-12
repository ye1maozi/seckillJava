package com.learn.seckill.exception;

import com.learn.seckill.constant.CodeMsg;

public class GlobalException extends Exception {
    private static final long servialVersionUID = 1L;
    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
