package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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
    private final SetmealMapper setmealMapper;

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


    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品页面查询dto
     * @return {@link PageResult }
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {

        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除菜品
     *
     * @param ids id
     */
    @Override
    @Transactional
    public void deleteBath(List<Long> ids) {

        // 判断当前菜品是否能够删除---是否存在起售中的菜品
        Long count = dishMapper.getStatusByIds(ids);
        if (count > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }

        // 判断当前菜品是否能够删除---是否被套餐关联
        List<Long> setmealIds = setmealMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            // 当前菜品被套餐关联，不能删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 如果直接批量删除 容易造成数据不同步
        // 删除菜品表中的菜品数据
        dishMapper.deleteByIds(ids);
        // 删除菜品关联的口味数据
        dishFlavorsMapper.deleteByDishIds(ids);

        //删除菜品表中的菜品数据
        // for (Long id : ids) {
        //     log.info("开始删除菜品id：{}", id);
        //     dishMapper.deleteById(id);
        //     log.info("删除菜品id：{}", id);
        //     //删除菜品关联的口味数据
        //     dishFlavorsMapper.deleteByDishId(id);
        //     log.info("删除菜品口味id：{}", id);
        // }
    }

    /**
     * 通过 id 获取风味
     *
     * @param id ID
     * @return {@link DishVO }
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // 根据id查询菜品数据
        Dish dish = dishMapper.getByIdWithFlavor(id);

        // 根据菜品id查询口味数据
        List<DishFlavor> dishFlavors = dishFlavorsMapper.getByDishId(id);

        // 将查询到的数据封装到VO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param dishDTO 菜DTO
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 更新菜品表
        dishMapper.update(dish);

        // 删除菜品表中的原有口味
        dishFlavorsMapper.deleteByDishId(dishDTO.getId());

        // 重新插入口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            // 遍历flavors集合，给每个flavor对象设置dishId属性
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            // 批量插入口味数据
            dishFlavorsMapper.insertBatch(flavors);
        }
    }
}
