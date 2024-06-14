package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

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
     * 批量插入口味数据
     *
     * @param flavors 口味
     */
    void insertBatch(List<DishFlavor> flavors);
}
