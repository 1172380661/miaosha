package com.wsw.miaosha.service;

import com.wsw.miaosha.dao.GoodsDao;
import com.wsw.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wsw
 * @Date: 2019/8/31 16:55
 */

@Service
public class GoodsService {

    private GoodsDao goodsDao;

    @Autowired
    public GoodsService(GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public List<GoodsVo> getGoodsVoList() {
        return goodsDao.getGoodsVoList();
    }

    public GoodsVo getGoodsVoById(Long goodsId) {
        return goodsDao.getGoodsVoById(goodsId);
    }
}
