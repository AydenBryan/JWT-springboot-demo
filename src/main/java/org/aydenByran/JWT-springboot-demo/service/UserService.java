package org.cold92.back.service;

import org.cold92.back.bean.User;

/**
 * @author admin
 */
public interface UserService {

    /**
     * 登录业务
     * @param username 用户名
     * @param password 密码
     * @return 返回给Controller，将相关用户信息装载入Payload构成令牌
     */
    User login(String username, String password);
}
