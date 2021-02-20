package org.cold92.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cold92.back.bean.User;
import org.cold92.back.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        throw new RuntimeException("login failure");
    }
}
