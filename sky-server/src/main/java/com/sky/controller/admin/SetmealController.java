package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐控制器
 *
 * @author ZJHAO
 * @date 2024/06/16
 */
@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@RequiredArgsConstructor
public class SetmealController {

    private final SetmealService setmealService;

    /**
     * 新增套餐
     *
     * @param setmealDTO 套餐DTO
     * @return {@link Result }
     */
    @PostMapping
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        log.info("新增套餐:{}", setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     * 分页查询套餐
     *
     * @param setmealPageQueryDTO 套餐DTO
     * @return {@link Result }<{@link SetmealVO }>
     */
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("分页查询套餐:{}", setmealPageQueryDTO);
        return Result.success(setmealService.pageQuery(setmealPageQueryDTO));
    }

    /**
     * 批量删除套餐
     *
     * @param ids id
     * @return {@link Result }
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("批量删除套餐:{}", ids);
        setmealService.deleteBath(ids);
        return Result.success();
    }

    /**
     * 通过id获取
     *
     * @param id
     * @return {@link Result }<{@link SetmealVO }>
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id) {
        log.info("根据id查询套餐:{}", id);
        return Result.success(setmealService.getById(id));
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO 套餐DTO
     * @return {@link Result }
     */
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        log.info("修改套餐:{}", setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("套餐起售停售:{},{}", status, id);
        setmealService.startOrStop(status, id);
        return Result.success();
    }
}
