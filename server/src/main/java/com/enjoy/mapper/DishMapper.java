package com.enjoy.mapper;

import com.enjoy.annotation.AutoFill;
import com.enjoy.dto.DishPageQueryDTO;
import com.enjoy.entity.Dish;
import com.enjoy.enumeration.OperationType;
import com.enjoy.vo.DishVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    
    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
    
    /**
     * 在菜品表中插入菜品
     *
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);
    
    /**
     * 菜品分类查询
     *
     * @param dishPageQueryDTO
     * @return DishVO
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
    
    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);
    
    /**
     * 根据主键id删除菜品数据
     *
     * @param id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);
    
    /**
     * 根据id修改菜品信息
     *
     * @param dish
     */
    void update(Dish dish);
    
    /**
     * 条件查询菜品
     *
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);
    
    /**
     * 根据套餐id查询菜品
     *
     * @param setmealId
     * @return
     */
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
    
    /**
     * 根据条件统计菜品数量
     *
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
