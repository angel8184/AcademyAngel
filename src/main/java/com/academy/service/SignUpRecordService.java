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
@Transactional
public class SignUpRecordService {

    @Autowired
    SignUpRecordDao signUpRecordDao;

    public void insertSignUpRecord(int stdntId, List<Academy0102Request_course> academy0101RequestCourseList ){

        for(Academy0102Request_course course : academy0101RequestCourseList){
            StdntSignUpRecord stdntSignUpRecord = new StdntSignUpRecord();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            stdntSignUpRecord.setRefStdntId(stdntId);
            stdntSignUpRecord.setRefCourseFeeId(course.getCourseId());
            stdntSignUpRecord.setCreateDate(timestamp);

            signUpRecordDao.save(stdntSignUpRecord);
        }


    }
}
