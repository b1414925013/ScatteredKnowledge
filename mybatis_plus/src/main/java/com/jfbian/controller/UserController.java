package com.jfbian.controller;


import com.jfbian.model.PageResult;
import com.jfbian.model.Result;
import com.jfbian.pojo.User;
import com.jfbian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bjf
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/userModer/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result<List<User>> selectAllTest() {
        return Result.success(userService.selectAllTest());
    }

    @GetMapping("/byid")
    public Result<User> selectByIdTest(Long id) {
        return Result.success(userService.selectByIdTest(id));
    }

    @PostMapping("/byid")
    public Result<User> selectByIdTest2(Long id) {
        return Result.success(userService.selectByIdTest(id));
    }

    @RequestMapping(value = "/byid2", method = RequestMethod.POST, produces = "application/json")
    public Result<User> selectByIdTest3(@RequestBody User user) {
        return Result.success(userService.selectByIdTest(user.getId()));
    }

    @GetMapping("/all2")
    public Result<PageResult<User>> selectAllTest2() {
        List<User> users = userService.selectAllTest();
        PageResult<User> listPageResult = new PageResult<User>(10, 1, users);
        return Result.success(listPageResult);
    }

}

