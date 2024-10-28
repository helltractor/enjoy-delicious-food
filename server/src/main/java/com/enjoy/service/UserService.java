package com.enjoy.service;

import com.enjoy.dto.UserLoginDTO;
import com.enjoy.entity.User;

public interface UserService {
    
    /**
     * 根据微信授权码实现微信登录
     *
     * @param userLoginDTO
     * @return
     */
    User wxlogin(UserLoginDTO userLoginDTO);
}
