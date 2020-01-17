package com.itheima.test;

import com.itheima.domain.Customer;
import com.itheima.utils.JpaUtils;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 测试jpa的保存
 * 1. 加载配置文件创建工厂(实体管理器工厂)对象
 * 2. 通过管理器类工厂获取实体管理器
 * 3. 获取食物对象,开启事务
 * 4. 完成增删改查操作
 * 5. 提交事务(回滚事务)
 * 6. 释放资源
 */

public class TestJPA {

    @Test
    public void testSave(){

        // 原始的创建对象的方法
        /* 1.创建工厂类对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(" ");
        // 2.通过工厂获取实体管理器
        EntityManager em = factory.createEntityManager(); */

        // 我们通过一个自定义的工具类去创建对象
        EntityManager em = JpaUtils.getEntityManager();

        // 获取事务对象,开启事务
        EntityTransaction tx = em.getTransaction();  // 获取事务对象
        tx.begin();  // 开启事务

        // 开始保存用户入库操作
        Customer customer = new Customer();
        customer.setCustName("刘飞天");
        customer.setCustIndustry("电问题啊");
        // 保存对象进数据库
        em.persist(customer);

        // 提交事务
        tx.commit();

        // 释放资源
        em.close();  // 关闭实体管理器

        // 因为我们通过工具类创建了一个公共的工厂,别的类还有可能用到公共的工厂
        // 所以我们不在此处关闭工厂
        // factory.close();  // 关闭工厂
    }

    // 根据id来查询客户
    /*
    * 1. 立即查询,当使用find()方法进行查询的时候,立马从数据库返回要查询的对象
    * */
    @Test
    public void testFind(){
        // 通过工具类获entityManager
        EntityManager em = JpaUtils.getEntityManager();

        // 开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 增删改查
        // 第一个参数: 查询数据的结果需要包装的实体类类型的字节码
        // id 查询的主键
        Customer customer = em.find(Customer.class, 1L);
        System.out.println(customer);

        // 提交事务
        tx.commit();

        // 释放资源
        em.close();
    }

    // getReference方法,根据id来查询客户
    /*
    * 1. getReference()方法进行查询的时候,首先返回的是是一个动态代理对象,而不是从数据库查询出的对象
    * 2. 什么时候使用查询对象的时候才会真正的发送查询sql语句去进行查询
    * 3. 这种查询方法也叫做 延迟加载(开发中一般用这种方式)
    * */
    @Test
    public void testGetReference(){
        // 通过工具类获entityManager
        EntityManager em = JpaUtils.getEntityManager();

        // 开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 增删改查
        // 第一个参数: 查询数据的结果需要包装的实体类类型的字节码
        // id 查询的主键
        Customer customer = em.getReference(Customer.class, 1L);
        System.out.println(customer);

        // 提交事务
        tx.commit();

        // 释放资源
        em.close();
    }

    // 根据id来进行删除
    @Test
    public void testRemove(){
        // 通过工具类获entityManager
        EntityManager em = JpaUtils.getEntityManager();

        // 开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 删除客户,先根据id查询出要删除的客户对象
        // 然后再对客户进行删除
        Customer customer = em.find(Customer.class, 1L);
        em.remove(customer);

        // 提交事务
        tx.commit();

        // 释放资源
        em.close();
    }


    // 更新操作
    @Test
    public void testUpdate(){
        // 通过工具类获entityManager
        EntityManager em = JpaUtils.getEntityManager();

        // 开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 更新客户,先根据id查询胡出需要被更新的对象
        // 把对象更新之后,然后保存到数据库
        Customer customer = em.find(Customer.class, 2L);
        customer.setCustName("贾铭威");
        Customer mergeCustomer = em.merge(customer);
        System.out.println(mergeCustomer);

        // 提交事务
        tx.commit();

        // 释放资源
        em.close();
    }

}
