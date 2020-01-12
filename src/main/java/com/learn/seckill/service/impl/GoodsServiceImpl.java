package com.learn.seckill.service.impl;

import com.learn.seckill.dao.SkGoodsMapper;
import com.learn.seckill.dao.SkGoodsSeckillMapper;
import com.learn.seckill.pojo.SkGoods;
import com.learn.seckill.pojo.SkGoodsSeckill;
import com.learn.seckill.service.GoodsService;
import com.learn.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "sk_goods")
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    SkGoodsMapper goodsMapper;

    @Autowired
    SkGoodsSeckillMapper skGoodsSeckillMapper;
    //乐观锁冲突最大重试次数
    private static final int DEFAULT_MAX_RETRIES = 5;


    @Cacheable(key = "'sk_goods'")
    public Object listGoods() {
        //todo 翻页支持
        List<GoodsVo> goods =  goodsMapper.listGoodsVo();
        return goods;
    }

    @Cacheable(key = "'sk_good_' +#p0 + '_'+#p1")
    public Object getDetailInfo(Long goodsId,Long seckillId) {
        if (seckillId == -1)
            seckillId = null;
        GoodsVo goods =  goodsMapper.listGoodsVoById(goodsId , seckillId);
        return goods;
    }

    @Override
    public SkGoods getGoodById(long goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    @Transactional
    public boolean reduceStock(GoodsVo goods) {
        SkGoodsSeckill g = new SkGoodsSeckill();
        g.setId(goods.getId());
        g.setVersion(goods.getVersion());

        int num =0;
        int ret = 0;
        do {
            num++;
            try{
                g.setVersion(skGoodsSeckillMapper.getVersionByGoodsId(goods.getId(),goods.getSeckillId()));
                ret =  skGoodsSeckillMapper.reduceStockByVersion(goods.getId(),goods.getSeckillId(),g.getVersion());
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("成功" + ret);
            if (ret != 0){
                break;
            }
        }while (num < DEFAULT_MAX_RETRIES);


        return   ret > 0;
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

}
