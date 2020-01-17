package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity  // 声明是一个实体类
@Table(name="sys_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    // 维护主键
    /*@ManyToMany(targetEntity = User.class)
    @JoinTable(name = "sys_user_role",
        joinColumns = {@JoinColumn(name = "sys_role_id",referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "sys_user_id",referencedColumnName = "user_id")}
    )*/
    // 放弃主键的维护
    @ManyToMany(mappedBy = "roles")  // mappedBy的取值是 User配置role的属性名称
    private Set<User> users = new HashSet<User>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
