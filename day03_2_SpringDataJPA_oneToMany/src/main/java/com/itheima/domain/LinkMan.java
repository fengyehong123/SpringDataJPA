package com.itheima.domain;


import javax.naming.Name;
import javax.persistence.*;

@Entity
@Table(name = "cst_linkman")
public class LinkMan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lkm_id")
    private Long lkmId;

    @Column(name = "lkm_name")
    private String lkmName;

    @Column(name = "lkm_gender")
    private String lkmGender;

    @Column(name = "lkm_phone")
    private String lkmPhone;

    @Column(name = "lkm_mobile")
    private String lkmMobile;

    @Column(name = "lkm_email")
    private String lkmEmail;

    @Column(name = "lkm_position")
    private String lkmPositio;

    @Column(name = "lkm_memo")
    private String lkmMemo;

    /*
    *  配置联系人到客户的多对一的关系
    * */
    @ManyToOne(targetEntity = Customer.class)  // 对应的实体类的字节码文件
    @JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")  // 外键名称 参照的表的主键
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getLkmId() {
        return lkmId;
    }

    public void setLkmId(Long lkmId) {
        this.lkmId = lkmId;
    }

    public String getLkmName() {
        return lkmName;
    }

    public void setLkmName(String lkmName) {
        this.lkmName = lkmName;
    }

    public String getLkmGender() {
        return lkmGender;
    }

    public void setLkmGender(String lkmGender) {
        this.lkmGender = lkmGender;
    }

    public String getLkmPhone() {
        return lkmPhone;
    }

    public void setLkmPhone(String lkmPhone) {
        this.lkmPhone = lkmPhone;
    }

    public String getLkmMobile() {
        return lkmMobile;
    }

    public void setLkmMobile(String lkmMobile) {
        this.lkmMobile = lkmMobile;
    }

    public String getLkmEmail() {
        return lkmEmail;
    }

    public void setLkmEmail(String lkmEmail) {
        this.lkmEmail = lkmEmail;
    }

    public String getLkmPositio() {
        return lkmPositio;
    }

    public void setLkmPositio(String lkmPositio) {
        this.lkmPositio = lkmPositio;
    }

    public String getLkmMemo() {
        return lkmMemo;
    }

    public void setLkmMemo(String lkmMemo) {
        this.lkmMemo = lkmMemo;
    }

    // 因为LinkMan和Customer对象相互关联,如果在 toString方法中两者都添加对方的打印的话,会发生内存溢出错误
    // 两者会相互调用
    @Override
    public String toString() {
        return "LinkMan{" +
                "lkmId=" + lkmId +
                ", lkmName='" + lkmName + '\'' +
                ", lkmGender='" + lkmGender + '\'' +
                ", lkmPhone='" + lkmPhone + '\'' +
                ", lkmMobile='" + lkmMobile + '\'' +
                ", lkmEmail='" + lkmEmail + '\'' +
                ", lkmPositio='" + lkmPositio + '\'' +
                ", lkmMemo='" + lkmMemo + '\'' +
                ", customer=" + customer +
                '}';
    }
}

