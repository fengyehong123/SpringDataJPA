package com.itheima.test;

import com.itheima.utils.JpaUtils;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

// jpql查询是JPA规范提供的一种面向对象的查询
// 可用于复杂的sql语句查询
public class JpqlTest {

    // 1. jpql查询全部
    @Test
    public void findAll(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        System.out.println("");
        tx.begin();
        // 开始用jpql查询全部
        // 1. 准备好要查询的语句 查询的是实体类对象,而不是数据库的表,我们已经把实体类对象和数据库的表进行了关联
        String jpql = "from com.itheima.domain.Customer";  // 不使用全限定类名也是可以进行查询的
        // Query 对象才是真正指定jpql的对象
        Query query = em.createQuery(jpql);
        // 因为查询的是全部,我们查询出来的是一个结果集
        List queryResultList = query.getResultList();

        for (Object obj : queryResultList) {
            System.out.println(obj);
        }

        tx.commit();
        em.close();
    }

    // 2. 排序查询,根据id来进行倒序查询
    @Test
    public void findOrders(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        // 开始用jpql查询全部
        // 1. 准备好要查询的语句 查询的是实体类对象,而不是数据库的表,我们已经把实体类对象和数据库的表进行了关联
        String jpql = "from com.itheima.domain.Customer order by custId desc";
        // Query 对象才是真正指定jpql的对象
        Query query = em.createQuery(jpql);
        // 因为查询的是全部,我们查询出来的是一个结果集
        List queryResultList = query.getResultList();

        for (Object obj : queryResultList) {
            System.out.println(obj);
        }

        tx.commit();
        em.close();
    }

    // 3.使用jpql查询,查询客户的总数
    @Test
    public void findCount(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        System.out.println();
        String jpql = "select count(custId) from Customer";
        // Query 对象才是真正指定jpql的对象
        Query query = em.createQuery(jpql);
        // 因为查询数量只能得到一个数据,我们用getSingleResult()方法查询得到唯一的结果集
        Object result = query.getSingleResult();
        System.out.println(result);

        tx.commit();
        em.close();
    }

    // 4.进行分页查询
    @Test
    public void pagedFind(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 先查询所有的对象
        String jpql = "from Customer";
        Query query = em.createQuery(jpql);

        // 对分页的参数进行赋值
        query.setFirstResult(0);
        query.setMaxResults(2);  // 每一次查询两条

        // 获得结果集
        List resultList = query.getResultList();
        for (Object obj : resultList) {
            System.out.println(obj);
        }

        tx.commit();
        em.close();
    }

    // 5.进行条件查询,查询以 贾 开头的客户
    @Test
    public void conditionFind(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // 先查询所有的对象
        String jpql = "from Customer where custName like ?";
        Query query = em.createQuery(jpql);

        // 开始对占位符进行补充  占位符的索引位置是从 1 开始的
        query.setParameter(1, "贾%");

        // 获得结果集
        List resultList = query.getResultList();
        for (Object obj : resultList) {
            System.out.println(obj);
        }

        tx.commit();
        em.close();
    }
}
