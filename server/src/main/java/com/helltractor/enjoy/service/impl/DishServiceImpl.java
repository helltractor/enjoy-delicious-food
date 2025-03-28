package com.helltractor.enjoy.service.impl;

import com.helltractor.enjoy.constant.MessageConstant;
import com.helltractor.enjoy.constant.StatusConstant;
import com.helltractor.enjoy.dto.DishDTO;
import com.helltractor.enjoy.dto.DishPageQueryDTO;
import com.helltractor.enjoy.entity.Dish;
import com.helltractor.enjoy.entity.DishFlavor;
import com.helltractor.enjoy.exception.DeletionNotAllowedException;
import com.helltractor.enjoy.mapper.DishFlavorMapper;
import com.helltractor.enjoy.mapper.DishMapper;
import com.helltractor.enjoy.mapper.SetmealDishMapper;
import com.helltractor.enjoy.result.PageResult;
import com.helltractor.enjoy.service.DishService;
import com.helltractor.enjoy.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    
    @Autowired
    private DishMapper dishMapper;
    
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    
    /**
     * 新增菜品和对应口味
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        
        Dish dish = new Dish();
        
        BeanUtils.copyProperties(dishDTO, dish);
        
        // 向菜品表插入一条数据
        dishMapper.insert(dish);
        
        // 获取insert语句生成的主键值
        Long dishId = dish.getId();
        
        List<DishFlavor> flavorList = dishDTO.getFlavors();
        if (flavorList != null && !flavorList.isEmpty()) {
            flavorList.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            // 向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavorList);
        }
    }
    
    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }
    
    /**
     * 菜品批量删除
     *
     * @param dishIds
     * @return
     */
    public void deleteBatch(List<Long> dishIds) {
        
        // 判断菜品的起售状态
        for (Long id : dishIds) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                // 菜品正在出售，无法删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        // 判断菜品是否被菜单关联
        List<Long> setmealdishIds = setmealDishMapper.getSetmealIdsByDishIds(dishIds);
        if (!setmealdishIds.isEmpty()) {
            // 菜品被套餐关联，无法删除
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 根据主键删除菜品数据
        for (Long id : dishIds) {
            dishMapper.deleteById(id);
            // 根据主键删除口味表数据
            dishFlavorMapper.deleteByDishId(id);
        }
    }
    
    /**
     * 根据id查询菜品及关联口味
     *
     * @param id
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        // 根据id查询菜品
        Dish dish = dishMapper.getById(id);
        
        // 根据dish_id查询菜品关联口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.getByDishId(id);
        
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }
    
    /**
     * 根据id修改菜品信息机关联口味信息
     *
     * @param dishDTO
     * @return
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        
        // 修改菜品基本信息
        dishMapper.update(dish);
        
        // 重新插入口味数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        List<DishFlavor> flavorList = dishDTO.getFlavors();
        if (flavorList != null && !flavorList.isEmpty()) {
            flavorList.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            // 向口味表插入n条数据
            dishFlavorMapper.insertBatch(flavorList);
        }
    }
    
    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);
        
        List<DishVO> dishVOList = new ArrayList<>();
        
        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);
            
            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());
            
            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }
    
    /**
     * 启用或停用菜品
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = dishMapper.getById(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }
    
    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }
}