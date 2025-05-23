package com.helltractor.enjoy.service;

import com.helltractor.enjoy.dto.UserLoginDTO;
import com.helltractor.enjoy.entity.User;

public interface UserService {

    /**
     * 根据微信授权码实现微信登录
     *
     * @param userLoginDTO
     * @return
     */
    User wxlogin(UserLoginDTO userLoginDTO);

}
