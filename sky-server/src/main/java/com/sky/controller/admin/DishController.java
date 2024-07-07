package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    private final RedisTemplate redisTemplate;

    /**
     * 新增菜品
     *
     * @param dishDTO 菜DTO
     * @return {@link Result }
     */
    @PostMapping()
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 清理缓存
        String key = "dish" + dishDTO.getCategoryId();
        cleanCache(key);

        return Result.success();
    }

    /**
     * 分页
     *
     * @param dishPageQueryDTO 菜品页面查询dto
     * @return {@link Result }<{@link PageResult }>
     */
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除
     *
     * @param ids id
     * @return {@link Result }
     */
    @DeleteMapping
    public Result deleteBath(@RequestParam List<Long> ids) {
        log.info("删除菜品：{}", ids);
        dishService.deleteBath(ids);

        // 清理所有菜品缓存（所有dish_开头的key）
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 通过id获取
     *
     * @param id ID
     * @return {@link Result }<{@link DishVO }>
     */
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 修改
     *
     * @param dishDTO 菜DTO
     * @return {@link Result }
     */
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        // 清理所有菜品缓存（所有dish_开头的key）
        cleanCache("dish_*");

        return Result.success();
    }

    /**
     * 起售或停售
     *
     * @param status 状态
     * @param id     ID
     * @return {@link Result }
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {

        dishService.startOrStop(status, id);

        // 清理所有菜品缓存（所有dish_开头的key）
        cleanCache("dish_*");


        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *
     * @param dishPageQueryDTO 菜品页面查询dto
     * @return {@link Result }<{@link List }<{@link DishVO }>>
     */
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类id查询菜品：{}", categoryId);
        List<DishVO> dishVOList = dishService.list(categoryId);
        return Result.success(dishVOList);
    }

    /**
     * 清理缓存数据
     *
     * @param pattern 模式
     */
    private void cleanCache(String pattern) {

        //redisTemplate.delete(redisTemplate.keys(pattern));
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
