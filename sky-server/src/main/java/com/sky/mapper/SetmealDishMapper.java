package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 套餐盘映射器
 *
 * @author ZJHAO
 * @date 2024/06/15
 */
@Mapper
public interface SetmealDishMapper {


    void deleteBySetmealIds(List<Long> ids);

    List<SetmealDish> getBySetmealId(Long id);

    @AutoFill(OperationType.INSERT)
    void insertBatch(List<SetmealDish> setmealDishes);

    void deleteBySetmealId(Long id);

    // List<SetmealDish> getSetmealIdsByDishId(Long id);
}
