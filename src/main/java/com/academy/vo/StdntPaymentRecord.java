package com.academy.vo;

import javax.persistence.*;
import java.sql.Timestamp;

public class StdntPaymentRecord {

    private int id;
    private int refStdntId;
    private int refSignupId;
    private int expense;
    private int grade;
    private String remark;
    private int paymentMonth;
    private int expenseMonthStart;
    private int expenseMonthEnd;
    private Timestamp createDate;
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
    @Column(name = "REF_SIGNUP_ID", nullable = false)
    public int getRefSignupId() { return refSignupId; }

    public void setRefSignupId(int refSignupId) { this.refSignupId = refSignupId; }

    @Basic
    @Column(name = "EXPENSE", nullable = false)
    public int getExpense() { return expense; }

    public void setExpense(int expense) { this.expense = expense; }

    @Basic
    @Column(name = "GRADE", nullable = false)
    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 20)
    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    @Basic
    @Column(name = "PAYMENT_MONTH", nullable = false)
    public int getPaymentMonth() { return paymentMonth; }

    public void setPaymentMonth(int paymentMonth) { this.paymentMonth = paymentMonth; }

    @Basic
    @Column(name = "EXPENSE_MONTH_START", nullable = false)
    public int getExpenseMonthStart() { return expenseMonthStart; }

    public void setExpenseMonthStart(int expenseMonthStart) { this.expenseMonthStart = expenseMonthStart; }

    @Basic
    @Column(name = "EXPENSE_MONTH_END", nullable = false)
    public int getExpenseMonthEnd() { return expenseMonthEnd; }

    public void setExpenseMonthEnd(int expenseMonthEnd) { this.expenseMonthEnd = expenseMonthEnd; }

    @Basic
    @Column(name = "CREATE_DATE", nullable = false)
    public Timestamp getCreateDate() { return createDate; }

    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }

    @Basic
    @Column(name = "PAY_DATE", nullable = true)
    public Timestamp getPayDate() { return payDate; }

    public void setPayDate(Timestamp payDate) { this.payDate = payDate; }
}
