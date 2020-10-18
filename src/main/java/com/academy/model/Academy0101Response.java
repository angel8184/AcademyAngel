package com.academy.model;

import java.util.List;

public class Academy0101Response {

    private String name;
    private String birth;
    private String idCard;
    private String parentName;
    private String phone;
    private String newNote;
    private String leaveNote;
    private String newDate;
    private String leaveDate;
    private String grade;
    private String handoutExemption;
    private String engDiscount;
    private String mathDiscount;
    private String remark;
    private String lastPaymentDate;
    private List<Academy0101Response_courseFeeList> courseFeeList;
    private List<Academy0101Response_paymentList> paymentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNewNote() {
        return newNote;
    }

    public void setNewNote(String newNote) {
        this.newNote = newNote;
    }

    public String getLeaveNote() {
        return leaveNote;
    }

    public void setLeaveNote(String leaveNote) {
        this.leaveNote = leaveNote;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHandoutExemption() {
        return handoutExemption;
    }

    public void setHandoutExemption(String handoutExemption) {
        this.handoutExemption = handoutExemption;
    }

    public String getEngDiscount() {
        return engDiscount;
    }

    public void setEngDiscount(String engDiscount) {
        this.engDiscount = engDiscount;
    }

    public String getMathDiscount() {
        return mathDiscount;
    }

    public void setMathDiscount(String mathDiscount) {
        this.mathDiscount = mathDiscount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public List<Academy0101Response_courseFeeList> getCourseFeeList() {
        return courseFeeList;
    }

    public void setCourseFeeList(List<Academy0101Response_courseFeeList> courseFeeList) {
        this.courseFeeList = courseFeeList;
    }

    public List<Academy0101Response_paymentList> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Academy0101Response_paymentList> paymentList) {
        this.paymentList = paymentList;
    }

    public Academy0101Response() {
    }
}
