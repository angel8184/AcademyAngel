package com.academy.service;

import com.academy.dao.SignUpRecordDao;
import com.academy.dao.StudentDao;
import com.academy.model.*;
import com.academy.util.DateTimeUtil;
import com.academy.vo.StdntSignUpRecord;
import com.academy.vo.StudentInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@Transactional(rollbackFor={RuntimeException.class, Exception.class, NumberFormatException.class})
public class StudentService {

    @Autowired
    StudentDao studentInfoDao;
    @Autowired
    SignUpRecordDao signUpRecordDao;

    @PersistenceContext
    EntityManager entityManager;

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


    public void insertStudentInfo(Academy0102Request academy0102Request) {

        StudentInfo studentInfo = new StudentInfo();

        if(null != academy0102Request.getStdntId()){
            studentInfo.setStdntId(academy0102Request.getStdntId());
        }
        studentInfo.setName(academy0102Request.getName());
        studentInfo.setBirth(academy0102Request.getBirth());
        studentInfo.setIdCard(academy0102Request.getIdCard());
        studentInfo.setParentName(academy0102Request.getParentName());
        studentInfo.setPhone(academy0102Request.getPhone());
        studentInfo.setLeaveNote(academy0102Request.isLeaveNote());
        studentInfo.setNewDate(academy0102Request.getNewDate());
        studentInfo.setLeaveDate(academy0102Request.getLeaveDate());
        studentInfo.setGrade(Integer.parseInt(academy0102Request.getGrade()));
        studentInfo.setHandoutExemption(academy0102Request.isHandoutExemption());
        studentInfo.setEngDiscount(academy0102Request.isEngDiscount());
        studentInfo.setMathDiscount(academy0102Request.isMathDiscount());
        studentInfo.setRemark(academy0102Request.getRemark());

        int stdntId = studentInfoDao.save(studentInfo).getStdntId();

        //先刪掉所有STDNTID 原先報名的課程
        List<StdntSignUpRecord> stdntSignUpRecordList = signUpRecordDao.findByRefStdntId(stdntId);

        if(!stdntSignUpRecordList.isEmpty()){
            signUpRecordDao.delete(stdntSignUpRecordList);
        }

        for(Academy0102Request_course course : academy0102Request.getCourseFeeList()){
            StdntSignUpRecord stdntSignUpRecord = new StdntSignUpRecord();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            course.setSignUpId(course.getSignUpId().equals( "") ? null : course.getSignUpId());

            if( course.getSignUpId() != null){
                stdntSignUpRecord.setId(Integer.parseInt(course.getSignUpId()));
            }

            stdntSignUpRecord.setRefStdntId(stdntId);

            stdntSignUpRecord.setRefCourseFeeId(Integer.parseInt(course.getCourseFeeId()));

            if(course.getSignUpStartMonth() == null || course.getSignUpStartMonth().equals("")){
                stdntSignUpRecord.setSignUpStartMonth(0);
            }else{
                stdntSignUpRecord.setSignUpStartMonth(Integer.parseInt(course.getSignUpStartMonth()));
            }

            if(course.getSignUpEndMonth() == null || course.getSignUpStartMonth().equals("")){
                stdntSignUpRecord.setSignUpEndMonth(0);
            }else{
                stdntSignUpRecord.setSignUpEndMonth(Integer.parseInt(course.getSignUpEndMonth()));
            }

            stdntSignUpRecord.setCreateDate(timestamp);

            signUpRecordDao.save(stdntSignUpRecord);
        }
    }

    public void updateLastPayDate(StudentInfo studentInfo){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        studentInfo.setLastPaymentDate(timestamp);

        studentInfoDao.save(studentInfo);
    }

    public List<StudentInfo> queryStudentData(String grade, String name)throws ParseException{

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT *");
        sql.append("  FROM student_info");
        sql.append(" WHERE 1=1");

        String querySql = getQuerySql(sql, grade, name);
        Query query = entityManager.createNativeQuery(querySql.toString(),StudentInfo.class);
        setQueryParameter(query, grade, name);


        return query.getResultList();
    }

    private String getQuerySql(StringBuilder sql, String grade, String name) throws ParseException {

        //年級
        if (StringUtils.isNotBlank(grade)) {
            sql.append(" and GRADE = :grade");
        }

        //姓名
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and NAME LIKE CONCAT('%', :name, '%') ");
        }

        sql.append(" ORDER BY GRADE DESC");

        return sql.toString();

    }

    private void setQueryParameter(Query query, String grade, String name) throws ParseException {

        //年級
        if (StringUtils.isNotBlank(grade)) {
            query.setParameter("grade", grade);
        }

        //姓名
        if (StringUtils.isNotBlank(name)) {
            query.setParameter("name", name);
        }

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

    public List<StudentInfo> findByGrades(int grade){

        List<StudentInfo> studentInfoList = new ArrayList<>();

        List<Integer> grades = new ArrayList<>();

        if( grade == 10){
            for(int i = 1; i < 7; i++){
                grades.add(i);
            }
        }else if( grade == 11){
            for(int i = 7; i < 10; i++){
                grades.add(i);
            }
        }else{
            grades.add(grade);
        }

        studentInfoList = studentInfoDao.findByGrades(grades);

        return studentInfoList;
    }


    public List<Academy0306Response> queryPaymentForPrintReceipt (Academy0306Request academy0306Request)throws ParseException{

        List<StudentInfo> studentInfoList = queryStudentData(academy0306Request.getGrade(), academy0306Request.getName());
        List<Academy0306Response> academy0306ResponseList = new ArrayList<>();

        if(!studentInfoList.isEmpty()){
            for(StudentInfo studentInfo : studentInfoList){
                if( !studentInfo.isLeaveNote()){
                    Academy0306Response academy0306Response = new Academy0306Response();

                    academy0306Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
                    academy0306Response.setName(studentInfo.getName());
                    academy0306Response.setGrade(getGradeName(studentInfo.getGrade()));
                    academy0306Response.setLastPaymentDate(studentInfo.getLastPaymentDate() == null ? "" :
                            studentInfo.getLastPaymentDate().toString().substring(0, 10));

                    academy0306ResponseList.add(academy0306Response);
                }
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
            academy0601Response.setBirth(studentInfo.getBirth() == null ? "" : DateTimeUtil.convertTWDate(studentInfo.getBirth().toString().substring(0,10)
                    ,"yyyy-MM-dd","yyy-MM-dd"));
            academy0601Response.setIdCard(studentInfo.getIdCard());
            academy0601Response.setGrade(gradeMap.get(studentInfo.getGrade()));
            academy0601Response.setParentName(studentInfo.getParentName());
            academy0601Response.setPhone(studentInfo.getPhone());
            academy0601Response.setLeaveNote(studentInfo.isLeaveNote() == true ? "Y" : "");
            academy0601Response.setNewDate(studentInfo.getNewDate() == null ? "" : DateTimeUtil.convertTWDate(
                    studentInfo.getNewDate().toString().substring(0,10),"yyyy-MM-dd","yyy-MM-dd"));
            academy0601Response.setLeaveDate(studentInfo.getLeaveDate() == null ? "" : DateTimeUtil.convertTWDate(
                    studentInfo.getLeaveDate().toString().substring(0,10),"yyyy-MM-dd","yyy-MM-dd"));
            academy0601Response.setHandoutExemption(studentInfo.isHandoutExemption() == true ? "Y" : "");
            academy0601Response.setEngDiscount(studentInfo.isEngDiscount() == true ? "Y" : "");
            academy0601Response.setMathDiscount(studentInfo.isMathDiscount() == true ? "Y" : "");
            academy0601Response.setRemark(studentInfo.getRemark());
            academy0601Response.setLastPaymentDate(studentInfo.getLastPaymentDate() == null ? "" : DateTimeUtil.convertTWDate(
                    studentInfo.getLastPaymentDate().toString().substring(0,10),"yyyy-MM-dd","yyy-MM-dd"));

            academy0601ResponseList.add(academy0601Response);
        }
        return academy0601ResponseList;
    }
}
