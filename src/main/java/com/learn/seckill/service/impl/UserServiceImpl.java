package com.learn.seckill.service.impl;


import com.learn.seckill.constant.CodeMsg;
import com.learn.seckill.dao.SkUserMapper;
import com.learn.seckill.exception.GlobalException;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.service.UserService;
import com.learn.seckill.util.MD5Util;
import com.learn.seckill.util.MobileUtil;
import com.learn.seckill.util.SpringContextUtil;
import com.learn.seckill.vo.LoginVo;
import com.learn.seckill.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = "sk_user")
public class UserServiceImpl implements UserService {

    @Autowired
    SkUserMapper userMapper;

    @Cacheable(key="'key_user_' + #p0")
    public SkUser getById(long id) {
        SkUser user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public boolean updatePassword(String token, long id, String formPass) throws GlobalException {
        SkUser user = getById(id);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        SkUser newUser = new SkUser();
        newUser.setId(user.getId());
        newUser.setPassword(MD5Util.formPassToDBPass(formPass,user.getSalt()));

        update(id,user);
        return true;
    }

    @Override
    public String login(LoginVo vo) throws GlobalException {
        if(vo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
//        String mobile = vo.getMobile();
        String password = vo.getPassword();

        SkUser user = userMapper.getByMobile(Long.parseLong(vo.getMobile()));
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password,salt);
        if(! calcPass.equals(dbPass)){
            throw  new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        String token = UUID.randomUUID().toString();
        UserService userService = SpringContextUtil.getBean(UserService.class);
        userService.addTokenUser(token,user);
        return token;
    }

    @Override
    @Cacheable(key="'key_token_' +#p0")
    public SkUser getByToken(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }
        return null;
    }

    @CachePut(key="'key_token_' +#p0")
    public SkUser addTokenUser(String token, SkUser user){

        return user;
    }
    @Override
    @CachePut(key="'key_user_'+#p0")
    public SkUser update(long id,SkUser user) {
        userMapper.updateByPrimaryKey(user);
        return user;
    }

    @Override
    public String register(UserVo user) throws GlobalException {
        if(user == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = user.getMobile();
        String password = user.getPassword();
        String nickname = user.getNickname();

        SkUser fuser = userMapper.getByMobileOrName(nickname,Long.parseLong(mobile));

        if(fuser != null){
            throw new GlobalException(CodeMsg.CONFILCT_REGISTER);
        }
        if(MobileUtil.sql_inj(nickname)){
            throw new GlobalException(CodeMsg.INVAILD_NICK);
        }
        fuser = new SkUser();

        fuser.setNickname(nickname);

        Date cur = new Date();
        fuser.setCreateTime(cur);
        fuser.setLastLoginTime(cur);

        fuser.setMobile(Long.parseLong(mobile));
        fuser.setHead("");
        fuser.setLoginCount(1);

        String salt = MD5Util.generate(MD5Util.salt);
        String psw = MD5Util.formPassToDBPass(password,salt);
        fuser.setSalt(salt);
        fuser.setPassword(psw);

        userMapper.insert(fuser);
        String token = UUID.randomUUID().toString();
        UserService userService = SpringContextUtil.getBean(UserService.class);
        userService.addTokenUser(token,fuser);
        return token;
    }
}
