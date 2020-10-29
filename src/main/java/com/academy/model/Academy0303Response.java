package com.academy.model;

import java.util.List;

public class Academy0303Response {

    private String paymentMonth;
    private List<Academy0301Response_courseFeeList> courseFeeList;

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
}
