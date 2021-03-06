package com.academy.model;

import java.sql.Timestamp;
import java.util.List;

public class Academy0102Request {

    private Integer stdntId;
    private String name;
    private Timestamp birth;
    private String idCard;
    private String parentName;
    private String phone;
    private boolean leaveNote;
    private Timestamp newDate;
    private Timestamp leaveDate;
    private String grade;
    private boolean handoutExemption;
    private boolean engDiscount;
    private boolean mathDiscount;
    private String remark;
    private boolean isUpdate;

    private List<Academy0102Request_course> courseFeeList;

    public Integer getStdntId() {
        return stdntId;
    }

    public void setStdntId(Integer stdntId) {
        this.stdntId = stdntId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
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

    public boolean isLeaveNote() {
        return leaveNote;
    }

    public void setLeaveNote(boolean leaveNote) {
        this.leaveNote = leaveNote;
    }

    public Timestamp getNewDate() {
        return newDate;
    }

    public void setNewDate(Timestamp newDate) {
        this.newDate = newDate;
    }

    public Timestamp getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Timestamp leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isHandoutExemption() {
        return handoutExemption;
    }

    public void setHandoutExemption(boolean handoutExemption) {
        this.handoutExemption = handoutExemption;
    }

    public boolean isEngDiscount() {
        return engDiscount;
    }

    public void setEngDiscount(boolean engDiscount) {
        this.engDiscount = engDiscount;
    }

    public boolean isMathDiscount() {
        return mathDiscount;
    }

    public void setMathDiscount(boolean mathDiscount) {
        this.mathDiscount = mathDiscount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public List<Academy0102Request_course> getCourseFeeList() {
        return courseFeeList;
    }

    public void setCourseFeeList(List<Academy0102Request_course> courseFeeList) {
        this.courseFeeList = courseFeeList;
    }
}
