package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 因为使用的是Spring框架整合之后,所以可以声明spring提供的单元测试环境
@RunWith(SpringJUnit4ClassRunner.class)
// 读取配置文件,指定spring容器的相关信息
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerDaoTest {

    // 从容器中获取customerDao对象,然后注入此处
    @Autowired
    private CustomerDao customerDao;


    // 1. 测试根据id来查询用户
    @Test
    public void testFindById(){
        // 因为ID是Long类型的,所以 查询的ID 为 2L
        // getOne() 延迟加载
        Customer customer = customerDao.findOne(1L);
        System.out.println(customer);
    }

    // 2. 保存或者更新数据
    /**
     * save( ) 保存或者更新
     * 根据传递的对象是否存在主键id来判断到底是保存还是更新
     *      有主键 ---> 先根据id查询数据,然后把查询到的数据更新
     *      没有主键 --- > 保存数据入库
     */
    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setCustName("贾AA");
        customer.setCustAddress("火星");
        Customer savedCustomer = customerDao.save(customer);
        // 打印保存好的对象
        System.out.println(savedCustomer);
    }
    // 测试更新
    @Test
    public void testUpdate(){
        Customer customer = new Customer();
        customer.setCustId(8L);
        customer.setCustName("贾AA");
        customer.setCustAddress("胶州");
        Customer savedCustomer = customerDao.save(customer);
        // 打印更新好的对象
        System.out.println(savedCustomer);
    }

    // 测试删除
    @Test
    public void testDelete(){
        // 会先从数据库里根据id查询是否有这个对象,如果有这个对象的话才会进行删除操作
        customerDao.delete(7L);
    }

    // 查询所有的用户
    @Test
    public void testFindAllCustomer(){
        List<Customer> customerList = customerDao.findAll();
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    // 查询客户的总的数量
    @Test
    public void testCount(){
        long count = customerDao.count();
        System.out.println(count);  // 4 一共有4条数据
    }

    // 判断id为4的客户是否存在
    @Test
    public void testExists(){
        boolean flag = customerDao.exists(4L);
        System.out.println(flag);  // true
    }

    // 根据id查询客户,延时加载的方式
    @Test
    @Transactional  // 在单元测试里面使用getOne( )方法的话,需要加上事务注解,这个注解就是为了保证getOne()方法的正常使用
    public void testGetOne(){
        // getOne( ) 方法的底层调用的是 getReference()方法,所以是延时加载
        // 返回的的是一个动态代理对象,什么时候用,什么时候发送查询语句
        // findOne() 立即加载
        Customer customer = customerDao.getOne(4L);
        System.out.println(customer);
    }






}
