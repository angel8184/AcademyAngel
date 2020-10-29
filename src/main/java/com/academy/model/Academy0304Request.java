package com.academy.model;

import java.util.List;

public class Academy0304Request {

    private String payMainId;
    private String grade;
    private String stdntId;
    private String paymentMonth;
    private List<Academy0304Request_courseFeeList> courseFeeList;

    public String getPayMainId() {
        return payMainId;
    }

    public void setPayMainId(String payMainId) {
        this.payMainId = payMainId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStdntId() {
        return stdntId;
    }

    public void setStdntId(String stdntId) {
        this.stdntId = stdntId;
    }

    public String getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public List<Academy0304Request_courseFeeList> getCourseFeeList() {
        return courseFeeList;
    }

    public void setCourseFeeList(List<Academy0304Request_courseFeeList> courseFeeList) {
        this.courseFeeList = courseFeeList;
    }
}
