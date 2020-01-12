package com.learn.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.learn.seckill.RabbitMq.MQSender;
import com.learn.seckill.RabbitMq.SeckillMessage;
import com.learn.seckill.constant.CodeMsg;
import com.learn.seckill.constant.RedisKey;
import com.learn.seckill.constant.Result;
import com.learn.seckill.pojo.SkOrder;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.service.GoodsService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.service.SeckillService;
import com.learn.seckill.service.UserService;
import com.learn.seckill.util.CookieUtil;
import com.learn.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("seckill")
public class seckillController implements InitializingBean {

    @Autowired
    SeckillService seckillService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    OrderService orderService;

    @Autowired
    MQSender sender;
    private HashMap<Long,Boolean> localOverMap = new HashMap<>();
    private RateLimiter rateLimiter = RateLimiter.create(10);
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> list = (List<GoodsVo>) goodsService.listGoods();
        if (null == list){
            return;
        }

        for (GoodsVo good : list){
            redisService.set(RedisKey.GoodsKey + good.getId(),good.getStockCount());
            localOverMap.put(good.getId(),false);
        }

    }

    @PostMapping("")
    public Object list(HttpServletRequest request, @RequestParam(value = "goodsId") Long goodsId,@RequestParam(value = "seckillId") Long seckillId) throws Exception {
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组

        if (cookies == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        String token = CookieUtil.hasCookie(cookies);
        if (   null == token){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        SkUser user = userService.getByToken(token);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
//        SkUser user = new SkUser();
//        user.setId(2L);

        if (! localOverMap.containsKey(goodsId)){
            return  Result.error(CodeMsg.SECKILL_GOODIS);
        }
        boolean over = localOverMap.get(goodsId);
        //没有库存
        if (over){
            return  Result.error(CodeMsg.SECKILL_OVER);
        }
        //预减库存
        long stock = redisService.decr(RedisKey.GoodsKey + goodsId,1);
        if (stock < 0){
            afterPropertiesSet();
            stock = redisService.decr(RedisKey.GoodsKey + goodsId,1);
            if (stock < 0){
                localOverMap.put(goodsId,true);
                return Result.error(CodeMsg.SECKILL_OVER);
            }
        }
        SkOrder order = orderService.getOrderByUserIdGoodsId(user.getId(),goodsId);
        //重复订单
        if (order != null){
            return  Result.error(CodeMsg.REPEATE_SECKILL);
        }

        //开始真实的秒杀

        SeckillMessage message = new SeckillMessage();
        message.setGoodsId(goodsId);
        message.setUser(user);
        message.setSeckillId(seckillId);
        sender.sendSeckillMessage(message);
        return Result.success("succe" +goodsId);
    }

    @GetMapping("")
    public Object getResult(HttpServletRequest request, @RequestParam(value = "goodsId") Long goodsId,@RequestParam(value = "seckillId") Long seckillId) throws Exception {
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        if (cookies == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        String token = CookieUtil.hasCookie(cookies);
        if (null == token) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        SkUser user = userService.getByToken(token);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        return seckillService.getSeckillResult(user,goodsId,seckillId);
    }

}
