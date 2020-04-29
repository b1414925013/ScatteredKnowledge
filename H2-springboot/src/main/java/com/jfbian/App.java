package com.jfbian;

import com.jfbian.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.jfbian.mapper")
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Resource
    private UserMapper userMapper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(userMapper.selectById(100L));
        userMapper.selectList(null).forEach(System.out::println);
    }
}
