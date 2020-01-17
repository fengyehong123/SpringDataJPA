package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.LinkManDao;
import com.itheima.domain.Customer;
import com.itheima.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    //测试对象导航查询（查询一个对象的时候，通过此对象查询所有的关联对象）
    @Test
    @Transactional // 解决在java代码中的no session问题 could not initialize proxy - no Session
    public void  testQuery1() {
        //查询id为1的客户
        Customer customer = customerDao.getOne(1L);
        //对象导航查询，此客户下的所有联系人
        Set<LinkMan> linkMans = customer.getLinkMans();

        for (LinkMan linkMan : linkMans) {
            System.out.println(linkMan);
        }
    }

    // 对象导航查询默认是使用延迟加载的形式进行查询的
    // 调用getOne()方法不会立即查询全部,而只是查询出 customer 的信息,当要用到customer所关联的LinkMan信息的时候,才会再一次进行查询
    // 我们可以修改配置,将延迟加载改为立即加载
    //  fetch  需要配置到多表映射关系的注解上,配置完之后 一查询 customer 就会把 customer 关联的 LinkMan 信息一并查询出来,不管你用不用
    @Test
    @Transactional
    public void  testQuery2() {
        //查询id为1的客户
        Customer customer = customerDao.findOne(1L);
        //对象导航查询，此客户下的所有联系人
        Set<LinkMan> linkMans = customer.getLinkMans();
        System.out.println(linkMans.size());
    }


    /*
    *  从联系人对象导航查询联系人所属的客户 从多的一方查一的一方
    *       从多的一方查一的一方 默认用的是立即加载,不管你用不用,立马给查出来
    *       我们可以在注解的配置中修改默认的加载方式,把立即加载改为延时加载,什么时候用,什么时候进行查询
    * */
    @Test
    @Transactional
    public void  testQuery3() {
        // 查询出 id 为 2 的联系人
        LinkMan linkMan = linkManDao.findOne(2L);

        // 对象导航查询所属的客户
        Customer customer = linkMan.getCustomer();
        System.out.println(customer);

    }

}
