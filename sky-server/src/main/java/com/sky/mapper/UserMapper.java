package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户映射器
 *
 * @author ZJHAO
 * @date 2024/07/06
 */
@Mapper
public interface UserMapper {

    /**
     * 通过 OpenID 获取用户
     *
     * @param openid OpenID
     * @return {@link User }
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 新增用户
     *
     * @param user 用户
     */
    void insert(User user);
}
