package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜品mapper
 *
 * @author ZJHAO
 * @date 2024/06/14
 */
@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 插入菜品
     *
     * @param dish 盘子
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO 菜品页面查询dto
     * @return {@link Page }<{@link DishVO }>
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 通过id获取状态
     *
     * @param ids id
     * @return {@link Long }
     */
    Long getStatusByIds(List<Long> ids);

    /**
     * 通过 ids 删除 Bath
     *
     * @param ids id
     */
    void deleteByIds(List<Long> ids);

    /**
     * 按id删除
     *
     * @param id ID
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    @Select("select * from dish where id = #{id}")
    Dish getByIdWithFlavor(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    List<DishVO> list(Dish dish);

    List<Dish> getBySetmealId(Long id);

    Integer countByCategoryIds(List<Dish> dishes);

}
