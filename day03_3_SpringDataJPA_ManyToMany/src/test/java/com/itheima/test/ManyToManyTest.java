package com.itheima.test;


import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    // 测试保存一个用户,保存一个角色
    @Test
    @Transactional
    @Rollback(false)
    public void testSave(){
        User user = new User();
        user.setUserName("贾铭威");
        user.setUserAge(18);;

        Role role = new Role();
        role.setRoleName("管理员");

        // 配置用户到角色的关系  配置了只有,用户表和角色表就是通过中间表相关联系的表
        // 被动选择的一方放弃主键的维护权,角色是被用户选择的,选择角色放弃主键的维护权
        user.getRoles().add(role);
        role.getUsers().add(user);

        // 保存用户
        userDao.save(user);
        roleDao.save(role);
    }

    // 测试级联操作,保存一个用户的同时,保存这个用户的关联角色
    @Test
    @Transactional
    @Rollback(false)
    public void testCasCadeAdd(){
        User user = new User();
        user.setUserName("贾飞天");
        user.setUserAge(19);;

        Role role = new Role();
        role.setRoleName("员工");

        // 配置用户到角色的关系  配置了只有,用户表和角色表就是通过中间表相关联系的表
        // 被动选择的一方放弃主键的维护权,角色是被用户选择的,选择角色放弃主键的维护权
        user.getRoles().add(role);
        role.getUsers().add(user);

        // 保存用户的同时,也保存和用户关联的角色
        userDao.save(user);
    }

    // 测试级联的删除,删除一个用户的同时也把这个用户关联的角色给删除  测试的话,要把配置文件中的 create给改成 update.不创建表.用已知的表
    @Test
    @Transactional
    @Rollback(false)
    public void testCasCadeRemove(){
        User user = userDao.findOne(1L);
        userDao.delete(user);
    }

}
