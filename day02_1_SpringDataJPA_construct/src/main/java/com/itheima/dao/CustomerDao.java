package com.itheima.dao;

import com.itheima.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.lang.ref.SoftReference;
import java.util.List;

// 编写符合SpringDataJpa的dao层接口规范
/* 1. 需要继承JpaRepository<T,TD>
            第一个泛型指 操作的实体类类型 ,第二个泛型指 实体类中主键属性的类型
            ** 这个类封装了基本CRUD操作
      和JpaSpecificationExecutor<T> 泛型是 操作的实体类类型
            ** 这个类封装了复杂的操作(例如分页查询)
*/
public interface CustomerDao extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer>{

    // 因为要用到复杂查询,所以我们定义接口,在接口上添加注解+sql语句

    // 根据姓名来查询客户
    @Query(value = " from Customer where custName = ? ")
    public Customer findName(String customerName);

    // 根据多个条件查询客户
    // 默认的情况下:占位符的位置需要和方法参数的位置保持一致,如果不一致,就会报错
    // 通过给占位符加索引,可以使占位符和参数位置不一致
    // from Customer where cust_address = ?2 and custName = ?1   ---> 第一个占位符和第二个参数绑定 第二个占位符和第一个参数绑定
    @Query(value = "from Customer where cust_address = ? and custName = ?")
    public Customer findCondition(String address,String customerName);

    // 根据id来更新客户的名称
    // ?后面的数字是为了绑定占位符和方法参数的
    @Query(value = " update Customer set custName = ?2 where custId = ?1 ")
    @Modifying  // 声明当前方法进行的是更新的操作,不加这个注解的话,这个操作就是查询操作
    public void updateCustomer(long id,String customerName);

    // -------------------------------------------------------------------------------

    // 使用sql语句进行查询
    /**
     * value的值可以用spql语句也可以用sql语句
     * nativeQuery 值为true表示用sql查询,为false表示用spql查询,默认是false
     */
    @Query(value = "select * from cst_customer",nativeQuery = true)
    // 返回值很特殊,是Object类型的数组
    public List<Object []> findBySql();

    // 添加条件进行查询
    @Query(value = "select * from cst_customer WHERE cust_name like ?1",nativeQuery = true)
    public List<Object []> findByOneCondition(String name);

    // ----------------------------------------------------------------------------------------

    // 通过命名规则进行查询

    /*
     * findBy :  表示查询
     * 对象中的属性名首字母大写: 表示要查询的条件
     * findByCustName: 表示根据客户的名称进行查询
     * */
    // 通过命名规则查询的话,框架会根据接口名称自动解析成sql语句
    public Customer findByCustName(String custName);

    // 通过命名规则进行模糊匹配查询
    public List<Customer> findByCustNameLike(String custName);

    /*
     *  多条件查询 中间可以使用 and 或者 or 等连接符
     *       使用客户名称模 糊匹配和客户地址精准匹配的查询
     * */
    public Customer findByCustNameLikeAndCustAddress(String custName,String custAddress);
}
