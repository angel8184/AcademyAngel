package com.academy.model;

public class Academy0304Request_courseFeeList {

    private String payRecordId;
    private String courseFeeId;
    private String remark;
    private String expense;
    private String expenseMonthStart;
    private String expenseMonthEnd;

    public String getPayRecordId() {
        return payRecordId;
    }

    public void setPayRecordId(String payRecordId) {
        this.payRecordId = payRecordId;
    }

    public String getCourseFeeId() { return courseFeeId; }

    public void setCourseFeeId(String courseFeeId) {
        this.courseFeeId = courseFeeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getExpenseMonthStart() {
        return expenseMonthStart;
    }

    public void setExpenseMonthStart(String expenseMonthStart) {
        this.expenseMonthStart = expenseMonthStart;
    }

    public String getExpenseMonthEnd() {
        return expenseMonthEnd;
    }

    public void setExpenseMonthEnd(String expenseMonthEnd) {
        this.expenseMonthEnd = expenseMonthEnd;
    }
}
