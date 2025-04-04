package com.helltractor.enjoy.controller.admin;

import com.helltractor.enjoy.constant.JwtClaimsConstant;
import com.helltractor.enjoy.dto.EmployeeDTO;
import com.helltractor.enjoy.dto.EmployeeLoginDTO;
import com.helltractor.enjoy.dto.EmployeePageQueryDTO;
import com.helltractor.enjoy.entity.Employee;
import com.helltractor.enjoy.properties.JwtProperties;
import com.helltractor.enjoy.result.PageResult;
import com.helltractor.enjoy.result.Result;
import com.helltractor.enjoy.service.EmployeeService;
import com.helltractor.enjoy.utils.JwtUtil;
import com.helltractor.enjoy.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/employee")
@Api(tags = "员工相关接口")
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private JwtProperties jwtProperties;
    
    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        
        Employee employee = employeeService.login(employeeLoginDTO);
        
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();
        
        return Result.success(employeeLoginVO);
    }
    
    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出")
    public Result<String> logout() {
        return Result.success();
    }
    
    /**
     * 新增员工
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result<String> Save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工： {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }
    
    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询： 参数为：{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }
    
    /**
     * 禁用启用员工账号
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("禁用启用员工账号")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("禁用启用员工账号：{} {}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }
    
    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }
    
    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     * @return
     */
    @PutMapping()
    @ApiOperation("编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}