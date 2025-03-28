package com.helltractor.enjoy.mapper;

import com.helltractor.enjoy.entity.Setmeal;
import com.helltractor.enjoy.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    
    /**
     * 判断当前菜品是否被套餐关联了
     *
     * @param dishIds
     */
    // select setmeal_id from setmeal_dish where dish_id in (1, 2, 4, 5)
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
    
    /**
     * 根据套餐id查询套餐信息
     *
     * @param setmealId
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long setmealId);
    
    /**
     * 保存套餐和菜品的关联关系
     *
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
    
    /**
     * 删除套餐餐品关系表中的数据
     *
     * @param id
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long id);
    
    /**
     * 根据套餐id查询套餐和菜品的关联关系
     *
     * @param id
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long id);
}