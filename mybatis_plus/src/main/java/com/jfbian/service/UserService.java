package com.jfbian.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jfbian.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bjf
 * @since 2020-09-17
 */
public interface UserService extends IService<User> {

    public List<User> selectAllTest();


    public User selectByIdTest(Long id);
}
