package com.academy.service;

import com.academy.dao.CourseFeeDao;
import com.academy.vo.CourseFeeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class})
public class CourseFeeService {

    @Autowired
    CourseFeeDao courseFeeDao;

    public List<CourseFeeInfo> queryAllCourseFee(){

        List<CourseFeeInfo> courseFeeInfoList = (List<CourseFeeInfo>)courseFeeDao.findAll();

        return courseFeeInfoList;
    }

    public void insertCourseFee(String courseFeeName){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        CourseFeeInfo courseFeeInfo = new CourseFeeInfo();
        courseFeeInfo.setName(courseFeeName);
        courseFeeInfo.setCreateDate(timestamp);

        courseFeeDao.save(courseFeeInfo);
    }

    public void deleteCourseFee(int courseFeeId){
        courseFeeDao.delete(courseFeeId);
    }
}
