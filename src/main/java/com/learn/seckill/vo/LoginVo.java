package com.learn.seckill.vo;

import com.learn.seckill.validator.IsMobile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class LoginVo implements Serializable {

    @NotNull
    private String password;

    @IsMobile
    @NotNull
    private String mobile;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
