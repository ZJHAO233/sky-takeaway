package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * 套餐服务
 *
 * @author ZJHAO
 * @date 2024/06/16
 */
public interface SetmealService {

    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐DTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 页面查询套餐
     *
     * @param setmealPageQueryDTO setmeal页面查询dto
     * @return {@link PageResult }
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     *
     * @param ids id
     */
    void deleteBath(List<Long> ids);

    /**
     * 修改套餐
     *
     * @param setmealDTO 套餐DTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 通过id获取
     *
     * @param id ID
     * @return {@link SetmealVO }
     */
    SetmealVO getById(Long id);

    /**
     * 起售停售
     *
     * @param status 地位
     * @param id     ID
     */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
