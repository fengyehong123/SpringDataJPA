package com.itheima.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity  // 指定这个类是一个实体类
@Table(name = "cst_customer")  // 指定这个实体类对应的数据库中的表
public class Customer {

    @Id  // 声明为主键
    // 主键生成策略为自动增长
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")  // 指定和表中的字段的映射关系
    private Long custId;

    @Column(name="cust_name")
    private String custName;

    @Column(name="cust_source")
    private String custSource;

    @Column(name="cust_industry")
    private String custIndustry;

    @Column(name="cust_level")
    private String custLevel;

    @Column(name="cust_address")
    private String custAddress;

    @Column(name="cust_phone")
    private String custPhone;

    public Long getCustId() {
        return custId;
    }

    // 配置客户和联系人之间的关系,一对多的关系,联系人不能重复,因此用的set集合
    // 使用注解的方式配置一对多
    // 在客户实体类上(一的一方)添加了外键配置,所以对于客户而言,也具备了维护外键的作用

    //@OneToMany(targetEntity = LinkMan.class)  // targetEntity 为目标实体类的类型的字节码文件
    // @JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")  // name属性值外键字段名称,referencedColumnName 主表的主键字段名称

    // 指定一对多中 一的一方放弃对外键的维护,用多的一方进行外键维护
    // 配置级联,操作一个对象的同时,连带着操作这个对象关联的其他对象
    /*
    *  CascadeType.ALL 所有
    *  CascadeType.MERGE 更新
    *  CascadeType.PERSIST 保存
    *  CascadeType.REMOVE 删除
    * */

    /*
    * fetch
    *   EAGER 立即加载
    *   LAZY  延迟加载
    * */
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    private Set<LinkMan> linkMans = new HashSet<LinkMan>();

    public Set<LinkMan> getLinkMans() {
        return linkMans;
    }

    public void setLinkMans(Set<LinkMan> linkMans) {
        this.linkMans = linkMans;
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
