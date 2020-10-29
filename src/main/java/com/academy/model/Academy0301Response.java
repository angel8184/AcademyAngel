package com.academy.model;

import java.util.List;

public class Academy0301Response {

    private String stdntId;
    private String stdntName;
    private String grade;
    private String payMainId;
    private String paymentMonth;
    private List<Academy0301Response_courseFeeList> courseFeeList;
    private String paymentCrDate;
    private String payDate;

    public String getStdntId() {
        return stdntId;
    }

    public void setStdntId(String stdntId) {
        this.stdntId = stdntId;
    }

    public String getStdntName() {
        return stdntName;
    }

    public void setStdntName(String stdntName) {
        this.stdntName = stdntName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getPayMainId() {
        return payMainId;
    }

    public void setPayMainId(String payMainId) {
        this.payMainId = payMainId;
    }

    public String getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public List<Academy0301Response_courseFeeList> getCourseFeeList() {
        return courseFeeList;
    }

    public void setCourseFeeList(List<Academy0301Response_courseFeeList> courseFeeList) {
        this.courseFeeList = courseFeeList;
    }

    public String getPaymentCrDate() {
        return paymentCrDate;
    }

    public void setPaymentCrDate(String paymentCrDate) {
        this.paymentCrDate = paymentCrDate;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
