package com.learn.seckill.controller;

import com.learn.seckill.constant.Result;
import com.learn.seckill.pojo.SkUser;
import com.learn.seckill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @GetMapping("")
    public Object listGoods(){

        Object o =  goodsService.listGoods();
        return Result.success(o);
    }

    @GetMapping("/{goodsId}/{seckillId}")
    public Object detailInfo(@PathVariable("goodsId") Long goodsId,@PathVariable("seckillId") Long seckillId){
        Object o =  goodsService.getDetailInfo(goodsId , seckillId);
        return Result.success(o);
    }
}
