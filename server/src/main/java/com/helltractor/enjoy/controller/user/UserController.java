package com.helltractor.enjoy.controller.user;

import com.helltractor.enjoy.constant.JwtClaimsConstant;
import com.helltractor.enjoy.dto.UserLoginDTO;
import com.helltractor.enjoy.entity.User;
import com.helltractor.enjoy.properties.JwtProperties;
import com.helltractor.enjoy.result.Result;
import com.helltractor.enjoy.service.UserService;
import com.helltractor.enjoy.utils.JwtUtil;
import com.helltractor.enjoy.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: helltractor
 * @Date: 2024/4/9 23:31
 */


@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "C端-用户接口")
public class UserController {
    
    // 用户服务
    @Autowired
    private UserService userService;
    
    // jwt配置
    @Autowired
    private JwtProperties jwtProperties;
    
    /**
     * C端用户登录-微信用户登录
     *
     * @param userLoginDTO
     * @return userLoginVO
     */
    @PostMapping("/user/login")
    @ApiOperation("登录")
    private Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        // 打印日志
        log.info("微信用户登录，授权码为：{}", userLoginDTO.getCode());
        
        // 调用微信登录接口
        User user = userService.wxlogin(userLoginDTO);
        
        // 生成token
        Map claims = new HashMap();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        
        // 返回用户信息
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }
}