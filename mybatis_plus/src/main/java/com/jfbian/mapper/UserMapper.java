package com.jfbian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfbian.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

// 在对应的Mapper上面继承基本的类 BaseMapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    // 所有的CRUD操作都已经编写完成了
    // 你不需要像以前的配置一大堆文件了！

    @Select("select * from user")
    public List<User> selectAllTest();

    @Select("select * from user where id=#{id}")
    public User selectByIdTest(Integer id);
}
