package com.academy.vo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="STUDENT_INFO")
public class StudentInfo {

    private int stdntId;
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
    private Timestamp lastPaymentDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STDNT_ID", nullable = false)
    public int getStdntId() { return stdntId; }

    public void setStdntId(int stdntId) { this.stdntId = stdntId; }

    @Basic
    @Column(name = "NAME", nullable = false, length = 20)
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Basic
    @Column(name = "BIRTH", nullable = false)
    public Timestamp getBirth() { return birth; }

    public void setBirth(Timestamp birth) { this.birth = birth; }

    @Basic
    @Column(name = "ID_CARD", nullable = false, length = 15)
    public String getIdCard() { return idCard; }

    public void setIdCard(String idCard) { this.idCard = idCard; }

    @Basic
    @Column(name = "PARENT_NAME", nullable = false, length = 20)
    public String getParentName() { return parentName; }

    public void setParentName(String parentName) { this.parentName = parentName; }

    @Basic
    @Column(name = "PHONE", nullable = false)
    public int getPhone() { return phone; }

    public void setPhone(int phone) { this.phone = phone; }

    @Basic
    @Column(name = "NEW_NOTE", nullable = true)
    public boolean isNewNote() { return newNote; }

    public void setNewNote(boolean newNote) { this.newNote = newNote; }

    @Basic
    @Column(name = "LEAVE_NOTE", nullable = true)
    public boolean isLeaveNote() { return leaveNote; }

    public void setLeaveNote(boolean leaveNote) { this.leaveNote = leaveNote; }

    @Basic
    @Column(name = "NEW_DATE", nullable = true)
    public Timestamp getNewDate() { return newDate; }

    public void setNewDate(Timestamp newDate) { this.newDate = newDate; }

    @Basic
    @Column(name = "LEAVE_DATE", nullable = true)
    public Timestamp getLeaveDate() { return leaveDate; }

    public void setLeaveDate(Timestamp leaveDate) { this.leaveDate = leaveDate; }

    @Basic
    @Column(name = "GRADE", nullable = false)
    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    @Basic
    @Column(name = "HANDOUT_EXEMPTION", nullable = true)
    public boolean isHandoutExemption() { return handoutExemption; }

    public void setHandoutExemption(boolean handoutExemption) { this.handoutExemption = handoutExemption; }

    @Basic
    @Column(name = "ENG_DISCOUNT", nullable = true)
    public boolean isEngDiscount() { return engDiscount; }

    public void setEngDiscount(boolean engDiscount) { this.engDiscount = engDiscount; }

    @Basic
    @Column(name = "MATH_DISCOUNT", nullable = true)
    public boolean isMathDiscount() { return mathDiscount; }

    public void setMathDiscount(boolean mathDiscount) { this.mathDiscount = mathDiscount; }

    @Basic
    @Column(name = "REMARK", nullable = true)
    public String getRemark() { return remark; }

    public void setRemark(String remark) { this.remark = remark; }

    @Basic
    @Column(name = "LAST_PAYMENT_DATE", nullable = true)
    public Timestamp getLastPaymentDate() { return lastPaymentDate; }

    public void setLastPaymentDate(Timestamp lastPaymentDate) { this.lastPaymentDate = lastPaymentDate; }

}
