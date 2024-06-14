package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜品服务实现
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final DishFlavorsMapper dishFlavorsMapper;

    /**
     * 新增菜品和口味
     *
     * @param dishDTO 菜DTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 向菜品添加1条数据
        dishMapper.insert(dish);
        // 获取insert语句生成的id值
        Long dishId = dish.getId();

        // 向口味表添加 n 条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null && !flavors.isEmpty()) {
            // 遍历flavors集合，给每个flavor对象设置dishId属性
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });

            dishFlavorsMapper.insertBatch(flavors);
        }
    }
}
