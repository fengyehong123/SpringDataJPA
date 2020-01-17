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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    @Test
    @Transactional  // 配置一个事务
    @Rollback(false)  // jap默认会给我们回滚,我们不让其回滚
    public void testAddCustomer(){
        // 创建一个客户,创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("李洛克");

        // 进行两个对象的关联,把联系人添加到顾客对象当中
        // 只有添加了两个对象的关系,才能在数据库当中生成外键
        customer.getLinkMans().add(linkMan);

        // 通过联系人添加客户的多对一的关系也是可以完成外键的配置的,和客户添加联系人等效
        //linkMan.setCustomer(customer);

        // 对创建的对象进行保存
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testAddCustomer2(){
        // 创建一个客户,创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("美团");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("李洛克");

        // 由于配置了多的一方到一的一方的关联关系(当保存的时候,就已经对外键赋值)
        // 由于配置了一的一方到多的一方的关联关系(会多发送一条冗余的update语句),只需要一的一方放弃维护外键即可解决
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        // 对创建的对象进行保存
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    // 级联操作保存客户的同时保存联系人入库
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd(){
        // 创建一个客户,创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("知乎");
        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("贾铭威");

        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        // 因为用了级联操作,只需要保存客户,与客户相关联的联系人也会被保存入库
        customerDao.save(customer);
    }

    // 测试级联删除,删除一个客户,跟客户关联的联系人也会被删除
    // 注意,要把配置文件中的 create改为 update,不让其重新创建数据库,否则无数据可删
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove(){
        Customer customer = customerDao.findOne(1L);
        // 删除这个客户,客户关联的联系人也会被删除
        customerDao.delete(customer);
    }
}
