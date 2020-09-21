package com.jfbian.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jfbian.mapper.UserMapper;
import com.jfbian.pojo.User;
import com.jfbian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author bjf
 * @since 2020-09-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllTest() {
        return userMapper.selectAllTest();
    }

    @Override
    public User selectByIdTest(Long id) {
        return userMapper.selectByIdTest(id);
    }
}
