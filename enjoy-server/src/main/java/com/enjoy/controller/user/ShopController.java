package com.enjoy.controller.user;

import com.enjoy.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: helltractor
 * @Date: 2024/4/9 15:53
 */

@Slf4j
@Api("店铺相关接口")
@RequestMapping("/user/shop")
@RestController("userShopController")
public class ShopController {
    
    private static final String SHOP_STATUS = "SHOP_STATUS";
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 获取店铺的营业状态
     *
     * @param
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        log.info("获取店铺的营业状态为：{}", status == 1 ? "营业中" : "休息中");
        return Result.success(status);
    }
}
