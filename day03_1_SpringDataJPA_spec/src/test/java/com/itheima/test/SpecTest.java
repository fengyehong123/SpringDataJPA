package com.itheima.test;


import com.itheima.dao.CustomerDao;
import com.itheima.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.persistence.criteria.*;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {

    // 自动注入
    @Autowired
    private CustomerDao customerDao;

    // 1. 根据条件查询单个对象
    @Test
    public void testSpec(){
        // 匿名内部类的写法
        // 要查询的对象是谁,泛型就是谁
        /*
        * 自定义查询条件
        *    1.实现Specification接口(提供泛型,查询的对象类型)
        *    2.实现toPredicate方法(构造查询条件)
        *    3.需要借助方法中的两个参数(
        *        root: 获取需要查询的对象属性
        *        CriteriaQuery: 构造查询条件,内部封装了很多的查询条件 (模糊匹配,精准匹配等)
        *           查询的方式都在 cb对象中
        *           用来比较的属性名称都在root对象中
        *    )
        * */
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 1. 获取比较的属性(是实现类的属性名不是数据库的字段名)
                Path<Object> custName = root.get("custName");

                // 2. 构造查询条件
                /*
                * 参数1 需要比较的属性(path对象)
                * 参数2 当前需要比较的取值
                * */
                Predicate predicate = cb.equal(custName, "贾铭威");// 进行精确的匹配
                // 返回查询到的结果
                return predicate;
            }
        };

        // 查询的条件可能是动态的,我们需要借助specification动态查询
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);  // Customer{custId=2, custName='贾铭威'......}
    }

    // 2. 根据多个条件去查询对象(客户的姓名和客户的地址)
    @Test
    public void testFindByConditions(){

        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 1.先获取比较的属性
                Path<Object> custName = root.get("custName");  // 客户名称
                Path<Object> custAddress = root.get("custAddress");  // 客户的地址

                // 2.构造查询的条件(两个查询条件都是精准匹配)
                Predicate p1 = cb.equal(custName, "贾铭威");
                Predicate p2 = cb.equal(custAddress, "胶南");

                // 3.将多个查询的条件匹配到一起 and 是表明两个条件要同时满足 or 表示两个条件满足一个即可
                Predicate predicate = cb.and(p1, p2);

                return predicate;
            }
        };

        // 我们根据多个条件只能查到一个数据,所以用的是 findOne
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }

    // 3. 根据客户的名臣进行模糊查询 + 查询的结果进行排序
    @Test
    public void testFindByLike(){

        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 得到客户名称Path对象
                Path<Object> custName = root.get("custName");
                // 查询方式,模糊匹配
                // 除了精准匹配之外的匹配要执行path对象的参数类型 path.as(参数的类型)
                Predicate predicate = cb.like(custName.as(String.class), "贾%");
                return predicate;
            }
        };

        /*
        *  构造一个排序的对象
        *  参数1: 排序的顺序 desc 倒序 asc 升序
        *  参数2: 排序依据的对象,要求是实体类的属性,而不是数据库的字段名
        * */
        Sort sort = new Sort(Sort.Direction.DESC, "custId");

        List<Customer> customerList = customerDao.findAll(spec,sort);
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    // 4. 分页查询
    @Test
    public void testFindPage(){

        /*
        * 构造分页查询的对象
        *   第一个参数: 当前查询的页数(从0开始) 0就是第一页
        *   第二个参数: 每页查询的数量
        *   从第一页查询,每一页显示两条数据
        * */
        PageRequest pageRequest = new PageRequest(0, 2);
        // 返回值是springDataJpa为我们封装好的pageBean对象 包含了数据列表,和总的条数
        // null表示没有查询条件,也就是说查询全部
        Page<Customer> customers = customerDao.findAll(null, pageRequest);

        // 得到总的条数
        System.out.println(customers.getTotalElements());  // 5
        // 得到总的页数
        System.out.println(customers.getTotalPages());  // 3
        // 得到数据的集合列表
        List<Customer> customerList = customers.getContent();
        for (Customer customer : customerList) {
            System.out.println(customer);  // Customer{custId=2, custName='贾铭威', custSource='null'}
        }
    }
}
