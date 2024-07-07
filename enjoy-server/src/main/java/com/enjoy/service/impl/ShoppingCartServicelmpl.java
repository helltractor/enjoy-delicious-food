package com.enjoy.service.impl;

import com.enjoy.context.BaseContext;
import com.enjoy.dto.ShoppingCartDTO;
import com.enjoy.entity.Dish;
import com.enjoy.entity.Setmeal;
import com.enjoy.entity.ShoppingCart;
import com.enjoy.mapper.DishMapper;
import com.enjoy.mapper.SetmealDishMapper;
import com.enjoy.mapper.ShoppingCartMapper;
import com.enjoy.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: helltractor
 * @Date: 2024/4/28 16:37
 */

@Service
@Slf4j
public class ShoppingCartServicelmpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    
    @Autowired
    private DishMapper dishMapper;
    
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        // 查询购物车中是否已经存在该商品
        
        // 将DTO转换为实体类
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        
        // 设置用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        
        // 如果存在，则更新商品数量
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            // 如果不存在，则添加商品到购物车
            Long dishId = shoppingCartDTO.getDishId();
            
            if (dishId != null) {
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Setmeal setmeal = setmealDishMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }
    }
    
    /**
     * 查询购物车列表
     *
     * @return
     */
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }
    
    /**
     * 减少购物车商品数量
     *
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() - 1);
            if (cart.getNumber() == 0) {
                shoppingCartMapper.deleteById(cart.getId());
            } else {
                shoppingCartMapper.updateNumberById(cart);
            }
        }
    }
    
    /**
     * 清空购物车
     *
     * @param
     */
    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }
}
