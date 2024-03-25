package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询对应的套餐id
     *
     * @param dishids
     */
    // select setmeal_id from setmeal_dish where dish_id in (1, 2, 4, 5)
    List<Long> getSetmealIdsByDishIds(List<Long> dishids);
}
