package com.academy.vo;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="STDNT_PAYMENT_RECORD_MAIN")
public class StdntPaymentRecordMain {

    private int id;
    private int refStdntId;
    private int grade;
    private int paymentMonth;
    private Timestamp createDate;
    private String receivingUnit;
    private Timestamp payDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Basic
    @Column(name = "REF_STDNT_ID", nullable = false)
    public int getRefStdntId() { return refStdntId; }

    public void setRefStdntId(int refStdntId) { this.refStdntId = refStdntId; }

    @Basic
    @Column(name = "GRADE", nullable = false)
    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    @Basic
    @Column(name = "PAYMENT_MONTH", nullable = false)
    public int getPaymentMonth() { return paymentMonth; }

    public void setPaymentMonth(int paymentMonth) { this.paymentMonth = paymentMonth; }

    @Basic
    @Column(name = "CREATE_DATE", nullable = false)
    public Timestamp getCreateDate() { return createDate; }

    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    @Basic
    @Column(name = "RECEIVING_UNIT", nullable = true)
    public String getReceivingUnit() { return receivingUnit; }

    public void setReceivingUnit(String receivingUnit) { this.receivingUnit = receivingUnit; }

    @Basic
    @Column(name = "PAY_DATE", nullable = true)
    public Timestamp getPayDate() { return payDate; }

    public void setPayDate(Timestamp payDate) { this.payDate = payDate; }
}
