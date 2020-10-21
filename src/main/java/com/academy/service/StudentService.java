package com.academy.service;

import com.academy.dao.StudentDao;
import com.academy.model.Academy0101Request;
import com.academy.model.Academy0102Request;
import com.academy.vo.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

    @Autowired
    StudentDao studentInfoDao;

    public int insertStudentInfo(Academy0102Request academy0102Request) {

        StudentInfo studentInfo = new StudentInfo();

        if(null != academy0102Request.getStdntId()){
            studentInfo.setStdntId(academy0102Request.getStdntId());
        }
        studentInfo.setName(academy0102Request.getName());
        studentInfo.setBirth(academy0102Request.getBirth());
        studentInfo.setIdCard(academy0102Request.getIdCard());
        studentInfo.setParentName(academy0102Request.getParentName());
        studentInfo.setPhone(academy0102Request.getPhone());
        studentInfo.setNewNote(academy0102Request.isNewNote());
        studentInfo.setLeaveNote(academy0102Request.isLeaveNote());
        studentInfo.setNewDate(academy0102Request.getNewDate());
        studentInfo.setLeaveDate(academy0102Request.getLeaveDate());
        studentInfo.setGrade(academy0102Request.getGrade());
        studentInfo.setHandoutExemption(academy0102Request.isHandoutExemption());
        studentInfo.setEngDiscount(academy0102Request.isEngDiscount());
        studentInfo.setMathDiscount(academy0102Request.isMathDiscount());
        studentInfo.setRemark(academy0102Request.getRemark());

        int stdntId = studentInfoDao.save(studentInfo).getStdntId();


        return stdntId;
    }

    public StudentInfo queryStudentData(Academy0101Request academy0101Request){

        StudentInfo studentInfo = studentInfoDao.findByGradeAndName(Integer.parseInt(academy0101Request.getGrade()), academy0101Request.getName());

        return studentInfo;

    }


}
