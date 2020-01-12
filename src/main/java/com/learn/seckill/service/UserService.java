package com.learn.seckill.service;

import com.learn.seckill.exception.GlobalException;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.vo.LoginVo;
import com.learn.seckill.vo.UserVo;

public interface UserService {

    SkUser getById(long id);

    boolean updatePassword(String token,long id,String formPass) throws GlobalException;

    String login(LoginVo vo) throws GlobalException;

    SkUser getByToken(String token);

    SkUser update(long id, SkUser user);

    String register(UserVo user) throws GlobalException;

    SkUser addTokenUser(String token, SkUser user);

}
