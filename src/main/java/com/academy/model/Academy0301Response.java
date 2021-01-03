package com.academy.model;

import java.util.ArrayList;
import java.util.List;

public class Academy0301Response {

    private String stdntId = "";
    private String stdntName = "";
    private String grade = "";
    private String payMainId = "";
    private String paymentMonth = "";
    private String paymentYear = "";
    private List<Academy0301Response_courseFeeList> courseFeeList = new ArrayList<>();
    private String paymentCrDate = "";
    private String payDate = "";
    private String receivingUnit = "";

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

    public String getPaymentYear() {
        return paymentYear;
    }

    public void setPaymentYear(String paymentYear) {
        this.paymentYear = paymentYear;
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

    public String getReceivingUnit() { return receivingUnit; }

    public void setReceivingUnit(String receivingUnit) { this.receivingUnit = receivingUnit; }
}
