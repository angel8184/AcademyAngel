package com.academy.service;

import com.academy.dao.StudentDao;
import com.academy.model.*;
import com.academy.vo.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StudentService {

    @Autowired
    StudentDao studentInfoDao;

    // Declaring the static map
    private static Map<Integer, String> gradeMap;

    // Instantiating the static map
    static
    {
        gradeMap = new HashMap<>();
        gradeMap.put(1, "一年級");
        gradeMap.put(2, "二年級");
        gradeMap.put(3, "三年級");
        gradeMap.put(4, "四年級");
        gradeMap.put(5, "五年級");
        gradeMap.put(6, "六年級");
        gradeMap.put(7, "國中一年級");
        gradeMap.put(8, "國中二年級");
        gradeMap.put(9, "國中三年級");
    }


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
        studentInfo.setGrade(Integer.parseInt(academy0102Request.getGrade()));
        studentInfo.setHandoutExemption(academy0102Request.isHandoutExemption());
        studentInfo.setEngDiscount(academy0102Request.isEngDiscount());
        studentInfo.setMathDiscount(academy0102Request.isMathDiscount());
        studentInfo.setRemark(academy0102Request.getRemark());

        int stdntId = studentInfoDao.save(studentInfo).getStdntId();


        return stdntId;
    }

    public void updateLastPayDate(StudentInfo studentInfo){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        studentInfo.setLastPaymentDate(timestamp);

        studentInfoDao.save(studentInfo);
    }

    public List<StudentInfo> queryStudentData(String grade, String name){

        List<StudentInfo> studentInfoList;

        if(grade == "" && name == ""){
            studentInfoList = studentInfoDao.findAllByOrderByGradeDesc();
        }else if(grade != "" && name == ""){
            studentInfoList = studentInfoDao.findByGrade(Integer.parseInt(grade));
        }else if(grade == "" && name != ""){
            studentInfoList = studentInfoDao.findByName(name);
        }else{
            studentInfoList = studentInfoDao.findByGradeAndName(Integer.parseInt(grade), name);
        }

        return studentInfoList;
    }

    public StudentInfo findbyStdntId(int stdntId){

        StudentInfo studentInfo = studentInfoDao.findByStdntId(stdntId);

        return studentInfo;
    }

    public StudentInfo findbyName(String name){

        StudentInfo studentInfo = studentInfoDao.findByName(name).get(0);

        return studentInfo;
    }

    public StudentInfo findbyStdntIdAndGrade(int stdntId, int grade){

        StudentInfo studentInfo = studentInfoDao.findByStdntIdAndGrade(stdntId, grade);

        return studentInfo;
    }

    public List<Academy0306Response> queryPaymentForPrintReceipt (Academy0306Request academy0306Request){

        List<StudentInfo> studentInfoList = queryStudentData(academy0306Request.getGrade(), academy0306Request.getName());
        List<Academy0306Response> academy0306ResponseList = new ArrayList<>();

        if(!studentInfoList.isEmpty()){
            for(StudentInfo studentInfo : studentInfoList){
                Academy0306Response academy0306Response = new Academy0306Response();

                academy0306Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
                academy0306Response.setName(studentInfo.getName());
                academy0306Response.setGrade(getGradeName(studentInfo.getGrade()));
                academy0306Response.setLastPaymentDate(studentInfo.getLastPaymentDate() == null ? "" :
                        studentInfo.getLastPaymentDate().toString().substring(0, 10));

                academy0306ResponseList.add(academy0306Response);
            }
        }
        return academy0306ResponseList;
    }

    public String getGradeName(int grade){
        return gradeMap.get(grade);
    }

    public void updateAllStudentGrade(List<StudentInfo> studentInfoList){

        for(StudentInfo studentInfo : studentInfoList){

            if(studentInfo.getGrade() < 9){
                studentInfo.setGrade(studentInfo.getGrade() + 1);

                studentInfoDao.save(studentInfo);
            }

        }
    }

    public List<Academy0601Response> fetchStudentInfoToResponse(List<StudentInfo> studentInfoList){

        List<Academy0601Response> academy0601ResponseList = new ArrayList<>();

        for(StudentInfo studentInfo : studentInfoList){

            Academy0601Response academy0601Response = new Academy0601Response();

            academy0601Response.setName(studentInfo.getName());
            academy0601Response.setBirth(studentInfo.getBirth().toString().substring(0,10));
            academy0601Response.setIdCard(studentInfo.getIdCard());
            academy0601Response.setGrade(gradeMap.get(studentInfo.getGrade()));
            academy0601Response.setParentName(studentInfo.getParentName());
            academy0601Response.setPhone(studentInfo.getPhone());
            academy0601Response.setNewNote(studentInfo.isNewNote() == true ? "Y" : "");
            academy0601Response.setLeaveNote(studentInfo.isLeaveNote() == true ? "Y" : "");
            academy0601Response.setNewDate(studentInfo.getNewDate() == null ? "" : studentInfo.getNewDate().toString().substring(0,10));
            academy0601Response.setLeaveDate(studentInfo.getLeaveDate() == null ? "" : studentInfo.getLeaveDate().toString().substring(0,10));
            academy0601Response.setHandoutExemption(studentInfo.isHandoutExemption() == true ? "Y" : "");
            academy0601Response.setEngDiscount(studentInfo.isEngDiscount() == true ? "Y" : "");
            academy0601Response.setMathDiscount(studentInfo.isMathDiscount() == true ? "Y" : "");
            academy0601Response.setRemark(studentInfo.getRemark());
            academy0601Response.setLastPaymentDate(studentInfo.getLastPaymentDate() == null ? "" : studentInfo.getLastPaymentDate().toString().substring(0,10));

            academy0601ResponseList.add(academy0601Response);

        }
        return academy0601ResponseList;
    }
}
