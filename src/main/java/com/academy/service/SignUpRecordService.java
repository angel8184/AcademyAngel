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
@Transactional(rollbackFor={RuntimeException.class, Exception.class, NumberFormatException.class})
public class SignUpRecordService {

    @Autowired
    SignUpRecordDao signUpRecordDao;

    public List<StdntSignUpRecord> queryStdntSignUpRecord(int stdntId){

        List<StdntSignUpRecord> stdntSignUpRecord = signUpRecordDao.findByRefStdntId(stdntId);

        return stdntSignUpRecord;

    }
}
