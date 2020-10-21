package com.academy.vo;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name="STDNT_PAYMENT_RECORD")
public class StdntPaymentRecord {

    private int id;
    private int refPaymentMainId;
    private int refSignupId;
    private int expense;
    private String remark;
    private int expenseMonthStart;
    private int expenseMonthEnd;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }


    @Basic
    @Column(name = "REF_PAYMENT_MAIN_ID", nullable = false)
    public int getRefPaymentMainId() { return refPaymentMainId; }

    public void setRefPaymentMainId(int refPaymentMainId) { this.refPaymentMainId = refPaymentMainId; }

    @Basic
    @Column(name = "REF_SIGNUP_ID", nullable = false)
    public int getRefSignupId() { return refSignupId; }

    public void setRefSignupId(int refSignupId) { this.refSignupId = refSignupId; }

    @Basic
    @Column(name = "EXPENSE", nullable = false)
    public int getExpense() { return expense; }

    public void setExpense(int expense) { this.expense = expense; }

    @Basic
    @Column(name = "REMARK", nullable = true, length = 20)
    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    @Basic
    @Column(name = "EXPENSE_MONTH_START", nullable = false)
    public int getExpenseMonthStart() { return expenseMonthStart; }

    public void setExpenseMonthStart(int expenseMonthStart) { this.expenseMonthStart = expenseMonthStart; }

    @Basic
    @Column(name = "EXPENSE_MONTH_END", nullable = false)
    public int getExpenseMonthEnd() { return expenseMonthEnd; }

    public void setExpenseMonthEnd(int expenseMonthEnd) { this.expenseMonthEnd = expenseMonthEnd; }
}
