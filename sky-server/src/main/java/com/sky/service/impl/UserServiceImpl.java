package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: helltractor
 * @Date: 2024/4/9 23:51
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatProperties weChatProperties;

    /**
     * 获取微信授权码
     *
     * @param code
     * @return
     */
    private String getOpenId(String code) {
        // 请求参数封装
        Map map = new HashMap();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        // 发送请求
        String result = HttpClientUtil.doGet(WX_LOGIN, map);

        // 打印日志
        log.info("微信登录返回结果：{}", result);

        // 解析返回结果
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getString("openid");
    }

    /**
     * 根据微信授权码实现微信登录
     *
     * @param userLoginDTO
     * @return
     */
    public User wxlogin(UserLoginDTO userLoginDTO) {

        // 获取微信登录凭证
        String code = userLoginDTO.getCode();

        /*
            // 拼接url
            String url = WX_LOGIN + "?appid=" + weChatProperties.getAppid() + "&secret=" + weChatProperties.getSecret() + "&js_code=" + code + "&grant_type=authorization_code";

            // 发送请求
            String result = HttpUtil.get(url);

            // 解析返回结果
            JSONObject jsonObject = JSON.parseObject(result);
            String openid = jsonObject.getString("openid");
        */

        // 获取openid
        String openid = getOpenId(code);

        // 判断是否获取到openid
        if (openid == null) {
            throw new RuntimeException(MessageConstant.LOGIN_FAILED);
        }

        // 查询用户是否存在
        User user = userMapper.selectByOpenid(openid);
        if (user == null) {
            // 不存在则新增用户
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            userMapper.insert(user);
        }
        return user;
    }

}
