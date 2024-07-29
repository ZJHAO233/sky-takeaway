package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 购物车映射器
 *
 * @author ZJHAO
 * @date 2024/07/12
 */
@Mapper
public interface ShoppingCartMapper {
    /**
     * 动态条件查询
     *
     * @param shoppingCart 购物车
     * @return {@link List }<{@link ShoppingCart }>
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 按 ID 修改商品数量
     *
     * @param shoppingCart 购物车
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart (name, image, dish_id, setmeal_id, dish_flavor, number, amount, user_id, create_time) " +
            "values (#{name}, #{image}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{userId}, #{createTime})")
    void insert(ShoppingCart shoppingCart);
}
