package com.jfbian;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfbian.mapper.UserMapper;
import com.jfbian.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AppTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void name() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    @Test
    public void name2() {
        List<User> users = userMapper.selectAllTest();
        users.forEach(System.out::println);
    }
    @Test
    public void name3() {
        User user = userMapper.selectByIdTest(3L);
        System.out.println(user);
    }
    // 测试插入
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("bbbbbb");
        user.setAge(3);
        user.setEmail("bbbbbb@qq.com");

        int result = userMapper.insert(user); // 帮我们自动生成id
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 发现，id会自动回填
    }
    // 测试更新
    @Test
    public void testUpdate(){
        User user = new User();
        // 通过条件自动拼接动态sql
        user.setId(1269806860853084169L);
        user.setName("bbbbbb");
        user.setAge(22);
        // 注意：updateById 但是参数是一个 对象！
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    // 测试乐观锁成功！
    @Test
    public void testOptimisticLocker(){
        // 1、查询用户信息
        User user = userMapper.selectById(1L);
        // 2、修改用户信息
        user.setName("kuangshen");
        user.setEmail("24736743@qq.com");
        // 3、执行更新操作
        userMapper.updateById(user);
    }


    // 测试乐观锁失败！多线程下
    @Test
    public void testOptimisticLocker2(){

        // 线程 1
        User user = userMapper.selectById(1L);
        user.setName("kuangshen111");
        user.setEmail("24736743@qq.com");

        // 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("kuangshen222");
        user2.setEmail("24736743@qq.com");
        userMapper.updateById(user2);

        // 自旋锁来多次尝试提交！
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
    }

    // 测试分页查询
    @Test
    public void testPage(){
        //  参数一：当前页
        //  参数二：页面大小
        //  使用了分页插件之后，所有的分页操作也变得简单的！
        Page<User> page = new Page<>(2,5);
        userMapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());

    }

    // 测试删除
    @Test
    public void testDeleteById(){
        userMapper.deleteById(1L);
    }
}
