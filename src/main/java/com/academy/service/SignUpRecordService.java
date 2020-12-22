package com.academy.service;

import com.academy.dao.SignUpRecordDao;
import com.academy.model.Academy0102Request_course;
import com.academy.vo.StdntSignUpRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional()
public class SignUpRecordService {

    @Autowired
    SignUpRecordDao signUpRecordDao;

    public void insertSignUpRecord(int stdntId, List<Academy0102Request_course> academy0101RequestCourseList ){

        //先刪掉所有STDNTID 原先報名的課程
        List<StdntSignUpRecord> stdntSignUpRecordList = this.queryStdntSignUpRecord(stdntId);

        if(!stdntSignUpRecordList.isEmpty()){
            signUpRecordDao.delete(stdntSignUpRecordList);
        }

        for(Academy0102Request_course course : academy0101RequestCourseList){
            StdntSignUpRecord stdntSignUpRecord = new StdntSignUpRecord();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            course.setSignUpId(course.getSignUpId().equals( "") ? null : course.getSignUpId());

            if( course.getSignUpId() != null){
                stdntSignUpRecord.setId(Integer.parseInt(course.getSignUpId()));
            }

            stdntSignUpRecord.setRefStdntId(stdntId);
            stdntSignUpRecord.setRefCourseFeeId(Integer.parseInt(course.getCourseFeeId()));
            stdntSignUpRecord.setSignUpStartMonth(Integer.parseInt(course.getSignUpStartMonth()));
            stdntSignUpRecord.setSignUpEndMonth(Integer.parseInt(course.getSignUpEndMonth()));
            stdntSignUpRecord.setCreateDate(timestamp);

            signUpRecordDao.save(stdntSignUpRecord);
        }
    }

    public List<StdntSignUpRecord> queryStdntSignUpRecord(int stdntId){

        List<StdntSignUpRecord> stdntSignUpRecord = signUpRecordDao.findByRefStdntId(stdntId);

        return stdntSignUpRecord;

    }
}
