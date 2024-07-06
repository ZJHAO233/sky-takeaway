package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * 用户服务
 *
 * @author ZJHAO
 * @date 2024/07/06
 */
public interface UserService{

    /**
     * 微信登录
     *
     * @param userLoginDTO 用户登录 DTO
     * @return {@link User }
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
