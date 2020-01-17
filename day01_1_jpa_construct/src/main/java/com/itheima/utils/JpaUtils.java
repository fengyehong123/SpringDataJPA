package com.itheima.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

// 创建一个工具类
// 为了解决实体管理器工厂的浪费资源和耗时的问题
// 通过静态代码块的形式,当程序第一次访问这个工具类时,创建一个公共的实体管理器工厂对象
public class JpaUtils {
    private static EntityManagerFactory factory;

    // 当程序第一次访问这个工具类的时候,通过静态代码块加载一个公共的对象
    static {
        // 1. 加载配置文件,创建entityManagerFactory
        factory = Persistence.createEntityManagerFactory("myJPA");
    }

    // 获取EntityManager对象
    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
}
