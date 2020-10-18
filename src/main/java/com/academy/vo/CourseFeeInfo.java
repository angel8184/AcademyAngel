package com.academy.vo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="COURSE_FEE_INFO")
public class CourseFeeInfo {

    private int id;
    private String name;
    private Timestamp createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Basic
    @Column(name = "NAME", nullable = false, length = 20)
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Basic
    @Column(name = "CREATE_DATE", nullable = false)
    public Timestamp getCreateDate() { return createDate; }

    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }
}
