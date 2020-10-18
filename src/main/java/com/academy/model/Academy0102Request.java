package com.academy.model;

import java.sql.Timestamp;
import java.util.List;

public class Academy0102Request {

    private Integer stdntId;
    private String name;
    private Timestamp birth;
    private String idCard;
    private String parentName;
    private int phone;
    private boolean newNote;
    private boolean leaveNote;
    private Timestamp newDate;
    private Timestamp leaveDate;
    private int grade;
    private boolean handoutExemption;
    private boolean engDiscount;
    private boolean mathDiscount;
    private String remark;
    private boolean isUpdate;

    private List<Academy0102Request_course> courseList;

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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public boolean isNewNote() {
        return newNote;
    }

    public void setNewNote(boolean newNote) {
        this.newNote = newNote;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
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

    public List<Academy0102Request_course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Academy0102Request_course> courseList) {
        this.courseList = courseList;
    }
}
