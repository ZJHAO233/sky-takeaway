package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 套餐服务实施
 *
 * @author ZJHAO
 * @date 2024/06/16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;
    private final DishMapper dishMapper;

    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐DTO
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //向套餐表插入数据
        setmealMapper.insert(setmeal);

        // 获取套餐id
        Long setmealId = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });

        //保存套餐和菜品的关联关系
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 页面查询套餐
     *
     * @param setmealPageQueryDTO setmeal页面查询dto
     * @return {@link PageResult }
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {

        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除套餐
     *
     * @param ids id
     */
    @Override
    @Transactional
    public void deleteBath(List<Long> ids) {
        // 判断是否有套餐正在售卖
       Integer count = setmealMapper.countByCategoryIds(ids);
        if (count > 0) {
            // 如果有正在售卖的套餐，抛出异常
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }

        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    /**
     * 通过id获取
     *
     * @param id ID
     * @return {@link SetmealVO }
     */
    @Override
    public SetmealVO getById(Long id) {
        // 根据id查询套餐数据
        Setmeal setmeal = setmealMapper.getById(id);
        // 根据套餐id查询套餐菜品数据
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO 套餐DTO
     */
    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 1. 修改套餐表
        setmealMapper.update(setmeal);
        // 2. 删除套餐和菜品的关联关系
        setmealDishMapper.deleteBySetmealId(setmeal.getId());

        // 3. 添加套餐和菜品的关联关系
        // 获取套餐id
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertBatch(setmealDishes);

    }
}
