package com.enjoy.service;

import com.enjoy.vo.BusinessDataVO;
import com.enjoy.vo.DishOverViewVO;
import com.enjoy.vo.OrderOverViewVO;
import com.enjoy.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkSpaceService {
    /**
     * 根据时间段统计营业数据
     *
     * @param begin
     * @param end
     * @return
     */
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);
    
    /**
     * 查询订单管理数据
     *
     * @return
     */
    OrderOverViewVO getOrderOverView();
    
    /**
     * 查询菜品总览
     *
     * @return
     */
    DishOverViewVO getDishOverView();
    
    /**
     * 查询套餐总览
     *
     * @return
     */
    SetmealOverViewVO getSetmealOverView();
}
