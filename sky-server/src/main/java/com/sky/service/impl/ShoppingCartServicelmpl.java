package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.ShoppingCartMapping;
import com.sky.service.ShoppingCartService;
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
    private ShoppingCartMapping shoppingCartMapping;

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

        List<ShoppingCart> list = shoppingCartMapping.list(shoppingCart);

        // 如果存在，则更新商品数量
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapping.updateNumberById(cart);
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
            shoppingCartMapping.insert(shoppingCart);
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
        List<ShoppingCart> list = shoppingCartMapping.list(shoppingCart);
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
        List<ShoppingCart> list = shoppingCartMapping.list(shoppingCart);
        if (list != null && list.size() > 0) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() - 1);
            if (cart.getNumber() == 0) {
                shoppingCartMapping.deleteById(cart.getId());
            } else {
                shoppingCartMapping.updateNumberById(cart);
            }
        }
    }

    /**
     * 清空购物车
     *
     * @param
     */
    public void deleteShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapping.deleteByUserId(userId);
    }
}
