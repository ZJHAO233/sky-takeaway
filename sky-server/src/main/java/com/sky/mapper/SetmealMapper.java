package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 套餐映射器
 *
 * @author ZJHAO
 * @date 2024/06/15
 */
@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 通过菜品 ID 获取套餐 ID
     *
     * @param ids id
     * @return {@link List }<{@link Long }>
     */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);
}
