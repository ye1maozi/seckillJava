package com.learn.seckill.vo;

import javax.validation.constraints.NotNull;

public class UserVo extends LoginVo {

    @NotNull
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
