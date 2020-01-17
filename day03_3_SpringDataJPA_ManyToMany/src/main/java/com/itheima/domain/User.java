package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity  // 声明是一个实体类
@Table(name="sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_age")
    private Integer userAge;

    /*
    *  配置用户到角色的多对多关系
    *       配置多对多的映射关系
    *           1. 声明表关系的配置
    *               @ManyToMany(targetEntity = Role.class) 多对多关系 targetEntity代表对方的实体类字节码
    *           2. 配置中间表(包含两个外键)
    * */

    // 配置多对多的关系 + 配置级联操作
    @ManyToMany(targetEntity = Role.class,cascade = CascadeType.ALL)
    // 配置多对多的中间表
    /* @JoinTable(name = "sys_user_role" name的值为中间表的名称
    *  要配置两个外键 User关联ROle表的外键和ROle表关联User表的外键
    * */
    @JoinTable(name = "sys_user_role",
        // joinColumns 当前对象在中间表中的外键 name的值为 外键的名称 referencedColumnName的值是 外键参照的主表的主键
        joinColumns = {@JoinColumn(name = "sys_user_id",referencedColumnName = "user_id")},
            // inverseJoinColumns 对方对象在中间表中的外键 referencedColumnName的值是 外键参照的主表的主键
            inverseJoinColumns = {@JoinColumn(name = "sys_role_id",referencedColumnName = "role_id")}
    )
    private Set<Role> roles = new HashSet<Role>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
