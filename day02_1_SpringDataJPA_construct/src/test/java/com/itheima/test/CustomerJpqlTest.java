package com.itheima.test;

import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CustomerJpqlTest {

    @Autowired
    private CustomerDao customerDao;

    //根据单个条件对用户进行查询
    @Test
    public void findName(){
        // 这个方法是我们在接口中自己定义的
        Customer customer = customerDao.findName("贾铭威");
        System.out.println(customer);
    }

    // 根据多个条件对用户进行查询
    @Test
    public void findCondition(){
        Customer byCondition = customerDao.findCondition("大杜各庄村", "贾BB");
        System.out.println(byCondition);
    }

    //根据id来更新客户名称
    @Test
    @Transactional  // 因为涉及到了数据库修改操作,就涉及到提交回滚数据,我们需要开启事务
    @Rollback(value = false)  // SpringDataJPA默认修改完成之后,回滚数据,我们把回滚改为false,让其提交数据
    public void updateCustomer(){
        customerDao.updateCustomer(4L,"刘备" );
    }

    // 用原生的sql进行查询
    @Test
    public void testFindSql(){
        List<Object[]> list = customerDao.findBySql();
        for (Object[] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    // 通过条件进行查询
    @Test
    public void testFindByCondition(){
        List<Object[]> lists = customerDao.findByOneCondition("贾%");
        for (Object[] list : lists) {
            System.out.println(Arrays.toString(list));
        }
    }

    // ---------------------------------------------------------------------
    // 通过命名规则进行查询
    @Test
    public void testRegulationName(){
        Customer obj = customerDao.findByCustName("贾飞天");
        System.out.println(obj);
    }

    // 模糊查询
    @Test
    public void testRegulationLikeName(){
        List<Customer> customerList = customerDao.findByCustNameLike("贾%");
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    // 多个条件进行查询
    @Test
    public void findByCustNameLikeAndCustAddress(){
        Customer customer = customerDao.findByCustNameLikeAndCustAddress("贾%", "胶州");
        System.out.println(customer);
    }

}
