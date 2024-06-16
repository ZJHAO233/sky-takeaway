package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜肴口味映射器
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@Mapper
public interface DishFlavorsMapper {
    /**
     * 按菜品 ID 批量删除
     *
     * @param ids id
     */
    void deleteByDishIds(List<Long> dishIds);

    /**
     * 按菜品 ID 删除
     *
     * @param dishId 菜品编号
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 通过菜品id获取
     *
     * @param id ID
     * @return {@link List }<{@link DishFlavor }>
     */
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);

    /**
     * 批量插入口味数据
     *
     * @param flavors 口味
     */
    void insertBatch(List<DishFlavor> flavors);
}
