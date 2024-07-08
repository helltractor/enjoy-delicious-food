package com.enjoy.mapper;

import com.enjoy.annotation.AutoFill;
import com.enjoy.dto.SetmealPageQueryDTO;
import com.enjoy.entity.Setmeal;
import com.enjoy.enumeration.OperationType;
import com.enjoy.vo.DishItemVO;
import com.enjoy.vo.SetmealVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealMapper {
    
    /**
     * 根据分类id查询套餐的数量
     * @param id
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);
    
    /**
     * 向套餐表插入数据
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
    
    /**
     * 分页查询
     * @param setmealPageQueryDTO
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    
    /**
     * 根据id查询数据
     * @param id
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);
    
    /**
     * 根据id删除数据
     * @param setmealId
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteById(Long setmealId);
    
    /**
     * 修改套餐表
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    
    /**
     * 条件查询
     * @param setmeal
     */
    List<Setmeal> list(Setmeal setmeal);
    
    /**
     * 根据id查询菜品选项
     * @param id
     */
    List<DishItemVO> getDishItemBySetmealId(Long id);
    
    /**
     * 根据条件统计套餐数量
     * @param map
     */
    Integer countByMap(Map map);
}