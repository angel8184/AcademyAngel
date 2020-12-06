package com.academy.model;

import java.util.List;

public class ReceiptMain {

    private String year;
    private String month;
    private String studentName;
    private String signUpCourseFee;
    private List<CourseFeeDetail> courseFeeDetailList;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSignUpCourseFee() {
        return signUpCourseFee;
    }

    public void setSignUpCourseFee(String signUpCourseFee) {
        this.signUpCourseFee = signUpCourseFee;
    }

    public List<CourseFeeDetail> getCourseFeeDetailList() {
        return courseFeeDetailList;
    }

    public void setCourseFeeDetailList(List<CourseFeeDetail> courseFeeDetailList) {
        this.courseFeeDetailList = courseFeeDetailList;
    }
}
