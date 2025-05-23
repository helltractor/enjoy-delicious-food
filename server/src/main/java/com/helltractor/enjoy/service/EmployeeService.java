package com.helltractor.enjoy.service;

import com.helltractor.enjoy.dto.EmployeeDTO;
import com.helltractor.enjoy.dto.EmployeeLoginDTO;
import com.helltractor.enjoy.dto.EmployeePageQueryDTO;
import com.helltractor.enjoy.entity.Employee;
import com.helltractor.enjoy.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     *
     * @param employeeDTO
     * @return
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 禁用启用员工账号
     *
     * @param status
     * @param id
     * @return
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     * @return
     */
    void update(EmployeeDTO employeeDTO);

}
