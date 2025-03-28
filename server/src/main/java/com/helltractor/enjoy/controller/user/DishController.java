package com.helltractor.enjoy.controller.user;

import com.helltractor.enjoy.constant.StatusConstant;
import com.helltractor.enjoy.dto.DishDTO;
import com.helltractor.enjoy.dto.DishPageQueryDTO;
import com.helltractor.enjoy.entity.Dish;
import com.helltractor.enjoy.result.PageResult;
import com.helltractor.enjoy.result.Result;
import com.helltractor.enjoy.service.DishService;
import com.helltractor.enjoy.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
    @Autowired
    private DishService dishService;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId) {
        // 构造reids缓存key
        String key = "dish_" + categoryId;
        
        // 从redis中获取缓存
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        
        // 缓存命中
        if (list != null && list.size() > 0) {
            return Result.success(list);
        }
        // 缓存未命中，查询数据库
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);//查询起售中的菜品
        
        // 查询菜品
        list = dishService.listWithFlavor(dish);
        
        // 缓存到redis
        redisTemplate.opsForValue().set(key, list);
        
        return Result.success(list);
    }
    
    /**
     * 清除缓存数据
     *
     * @param pattern
     */
    private void cleanCache(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
    
    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        
        // 新增菜品
        dishService.saveWithFlavor(dishDTO);
        
        // 清除缓存
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        
        return Result.success();
    }
    
    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result query(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    
    /**
     * 菜品批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("菜品批量删除：{}", ids);
        dishService.deleteBatch(ids);
        
        // 删除所有菜品的缓存数据
        cleanCache("dish_*");
        return Result.success();
    }
    
    /**
     * 修改菜品
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品：", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        
        // 删除所有菜品的缓存数据
        cleanCache("dish_*");
        return Result.success();
    }
    
    /**
     * 菜品起售停售
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result updateStatus(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);
        
        // 删除所有菜品的缓存数据
        cleanCache("dish_*");
        return Result.success();
    }
}