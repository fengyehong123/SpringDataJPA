package com.itheima.test;

import com.itheima.domain.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class QQ {

    Specification<Customer> spec = new Specification<Customer>() {
        // 运行了匿名内部类,在接口的内部对接口的方法进行实现
        public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            // 数据库字段对应的实现类的属性名
            Path<Object> custName = root.get("custName");
            // 属性名和属性值对应
            Predicate predicate = cb.equal(custName, "贾铭威");
            // 返回查询到的结果
            return predicate;
        }
    };
}
