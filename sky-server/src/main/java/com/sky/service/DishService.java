package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    /**
     * 新增菜品和口味
     *
     * @param dishDTO 菜DTO
     */
    void saveWithFlavor(DishDTO dishDTO);


    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品页面查询dto
     * @return {@link PageResult }
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);


    /**
     * 批量删除菜品
     *
     * @param ids id
     */
    void deleteBath(List<Long> ids);

    /**
     * 通过 id 获取风味
     *
     * @param id ID
     * @return {@link DishVO }
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param dishDTO 菜DTO
     */
    void updateWithFlavor(DishDTO dishDTO);
}
