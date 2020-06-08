package com.jfbian.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //@TableId(type = IdType.ID_WORKER) //默认的主键策略,雪花算法
    @TableId(type = IdType.AUTO) //使用自动增长,必须在数据库设置处将自增勾选
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @Version //乐观锁Version注解
    private Integer version;

    @TableLogic //逻辑删除
    private Integer deleted;
    // 字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
