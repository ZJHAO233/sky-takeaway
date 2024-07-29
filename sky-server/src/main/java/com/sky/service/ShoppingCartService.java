package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

/**
 * 购物车服务
 *
 * @author ZJHAO
 * @date 2024/07/12
 */
public interface ShoppingCartService {
    /**
     * 添加购物车
     *
     * @param shoppingCartDTO 购物车 DTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);
}
