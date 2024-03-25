package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 在口味表中批量插入菜品口味
     * @param flavorList
     */
    @AutoFill(value = OperationType.INSERT)
    void insertBatch(List<DishFlavor> flavorList);

    /**
     * 根据菜品id删除对应的口味数据
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long id);

    /**
     * 根据id查询菜品关联口味
     *
     * @param dishId
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
