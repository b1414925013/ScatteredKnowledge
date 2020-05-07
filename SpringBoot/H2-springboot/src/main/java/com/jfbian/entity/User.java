package com.jfbian.entity;


import lombok.Data;

/**
 * @author April Chen
 * @date 2019/9/2 17:03
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}