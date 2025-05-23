package com.helltractor.enjoy.service;

import com.helltractor.enjoy.dto.ShoppingCartDTO;
import com.helltractor.enjoy.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加商品到购物车
     *
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 展示购物车
     *
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * 减少购物车商品数量
     *
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车
     *
     * @param
     */
    void cleanShoppingCart();

}
