package com.learn.seckill.controller;

import com.learn.seckill.constant.Result;
import com.learn.seckill.exception.GlobalException;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.service.UserService;
import com.learn.seckill.util.CookieUtil;
import com.learn.seckill.util.MD5Util;
import com.learn.seckill.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @PostMapping("")
    public Object register(HttpServletResponse res, @Valid UserVo user) throws GlobalException {
        String token = userService.register(user);
        CookieUtil.addCookie(res,token);
        return Result.success(token);
    }
}
