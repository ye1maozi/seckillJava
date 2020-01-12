package com.learn.seckill.controller;


import com.learn.seckill.constant.Result;
import com.learn.seckill.exception.GlobalException;
import com.learn.seckill.service.UserService;
import com.learn.seckill.util.CookieUtil;
import com.learn.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("login")
public class LoginController {


    @Autowired
    UserService userService;


    @PostMapping("")
    public Object dologin(HttpServletResponse res, @RequestBody LoginVo loginVo) throws GlobalException {
        String token = userService.login(loginVo);
        CookieUtil.addCookie(res,token);
        return Result.success(token);
    }


}
