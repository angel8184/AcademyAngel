package com.academy.vo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="STDNT_SIGN_UP_RECORD")
public class StdntSignUpRecord {

    private int id;
    private int refStdntId;
    private int refCourseFeeId;
    private Timestamp createDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    @Basic
    @Column(name = "REF_STDNT_ID", nullable = false)
    public int getRefStdntId() { return refStdntId; }

    public void setRefStdntId(int refStdntId) { this.refStdntId = refStdntId; }

    @Basic
    @Column(name = "REF_COURSE_FEE_ID", nullable = false)
    public int getRefCourseFeeId() { return refCourseFeeId; }

    public void setRefCourseFeeId(int refCourseFeeId) { this.refCourseFeeId = refCourseFeeId; }

    @Basic
    @Column(name = "CREATE_DATE", nullable = false)
    public Timestamp getCreateDate() { return createDate; }

    public void setCreateDate(Timestamp createDate) { this.createDate = createDate; }
}
