package com.enjoy.service;

import com.enjoy.dto.DishDTO;
import com.enjoy.dto.DishPageQueryDTO;
import com.enjoy.entity.Dish;
import com.enjoy.result.PageResult;
import com.enjoy.vo.DishVO;

import java.util.List;

public interface DishService {
    
    /**
     * 新增菜品和对应口味
     *
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);
    
    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return PageResult
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
    
    /**
     * 菜品批量删除
     *
     * @param ids
     */
    void deleteBatch(List<Long> ids);
    
    /**
     * 根据id查询菜品
     *
     * @param id
     * @return DishVO
     */
    DishVO getByIdWithFlavor(Long id);
    
    /**
     * 根据id修改菜品信息机关联口味信息
     *
     * @param dishDTO
     * @return
     */
    void updateWithFlavor(DishDTO dishDTO);
    
    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
    
    /**
     * 启用或停用
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
    
    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);
}
