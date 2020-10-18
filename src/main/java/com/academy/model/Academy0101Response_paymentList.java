package com.academy.model;

import java.util.List;

public class Academy0101Response_paymentList {

    private List<Academy0101Response_lessonList> lessonList;
    private String paymentCrDate;
    private String payDate;

    public List<Academy0101Response_lessonList> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Academy0101Response_lessonList> lessonList) {
        this.lessonList = lessonList;
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
