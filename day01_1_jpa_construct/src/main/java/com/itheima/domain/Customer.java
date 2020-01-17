package com.itheima.domain;

/*
*  配置实体类和表的映射关系
*      1. 实体类和表的映射关系
*           @Entity
*           @Table
 *      2. 实体类中的属性和表中字段的映射关系
 *          @Id
 *          @GeneratedValue
 *              GenerationType.IDENTITY 自动增长 mysql使用
 *                 主键生成策略要想使用,底层数据库必须支持数据库自动增长
 *                 这个注解会使用底层数据库支持的主键增长方式,对id自动增长
 *              GenerationType.SEQUENCE 序列 oracle使用
 *              GenerationType.TABLE     表  jpa提供的一种机制,通过一张数据库表的形式帮助我们完成主键自增
 *              GenerationType.AUTO      由程序帮助我们自动的选择主键的生成策略
* */

import javax.persistence.*;

@Entity  // 声明这个类是一个实体类
@Table(name = "cst_customer")  // 配置实体类和表的映射关系,name表示实体类映射的表名
public class Customer {
    @Id  // 声明这个属性对应的是数据库的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 配置主键生成策略
    @Column(name = "cust_id")  // 配置数据库id字段和实体类属性名之间的映射关系
    private Long custId;

    // 配置数据库字段和实体类属性之间的映射关系
    @Column(name = "cust_name")
    private String custName;

    @Column(name = "cust_source")
    private String custSource;

    @Column(name = "cust_industry")
    private String custIndustry;

    @Column(name = "cust_level")
    private String custLevel;

    @Column(name = "cust_address")
    private String custAddress;

    @Column(name = "cust_phone")
    private String custPhone;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }
}
