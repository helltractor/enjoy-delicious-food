package com.sky.mapper;

import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据套餐id查询套餐信息
     *
     * @param setmealId
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long setmealId);
}
