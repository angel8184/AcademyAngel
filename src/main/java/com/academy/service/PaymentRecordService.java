package com.academy.service;

import com.academy.dao.PaymentRecordDao;
import com.academy.dao.PaymentRecordMainDao;
import com.academy.dao.StudentDao;
import com.academy.model.*;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import com.academy.vo.StudentInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class PaymentRecordService {

    @Autowired
    PaymentRecordMainDao paymentRecordMainDao;
    @Autowired
    PaymentRecordDao paymentRecordDao;
    @Autowired
    StudentDao studentInfoDao;
    @Autowired
    StudentService studentService;

    @PersistenceContext
    EntityManager entityManager;


    public List<StdntPaymentRecordMain> queryPaymentRecordMain(){

        List<StdntPaymentRecordMain> stdntPaymentRecordMainList = paymentRecordMainDao.findAllByOrderByGradeDesc();

        return stdntPaymentRecordMainList;
    }

    public StdntPaymentRecordMain queryPaymentRecordMainByStdntIdAndPaymentMonth(int stdntId, int month){

        StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordMainDao.findByRefStdntIdAndPaymentMonth(stdntId, month);

        return stdntPaymentRecordMain;
    }

    public StdntPaymentRecordMain queryPaymentRecordMainByStdntIdAndGrade(int stdntId, int grade){

        StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordMainDao.findByRefStdntIdAndGradeOrderByPaymentMonthDesc(stdntId, grade).get(0);

        return stdntPaymentRecordMain;
    }

    public StdntPaymentRecordMain queryPaymentRecordMainByStdntIdAndGradeAndPaymentMonth(int stdntId, int grade, int month){

        StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordMainDao.findByRefStdntIdAndGradeAndPaymentMonth(stdntId, grade, month);

        return stdntPaymentRecordMain;
    }

    public StdntPaymentRecordMain queryPaymentRecordMainById(int mainId){

        StdntPaymentRecordMain stdntPaymentRecordMain = paymentRecordMainDao.findById(mainId);

        return stdntPaymentRecordMain;
    }

    public List<StdntPaymentRecordMain> queryPaymentRecordMainByGradeAndMonth(int grade, int month){

        List<StdntPaymentRecordMain> stdntPaymentRecordMainList = paymentRecordMainDao.findByGradeAndPaymentMonth(grade, month);

        return stdntPaymentRecordMainList;
    }

    //findByGradesAndPaymentMonth
    public List<StdntPaymentRecordMain> queryPaymentRecordMainByGradesAndMonth(int grade, int month){

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

        List<StdntPaymentRecordMain> stdntPaymentRecordMainList = paymentRecordMainDao.findByGradesAndPaymentMonthOrderByGrade(grades, month);

        return stdntPaymentRecordMainList;
    }

    public int insertPaymentRecordMain(Academy0304Request academy0304Request){

        int stdntId = Integer.parseInt(academy0304Request.getStdntId());
        int grade = Integer.parseInt(academy0304Request.getGrade());
        int paymentMonth = Integer.parseInt(academy0304Request.getPaymentMonth());
        int year = Integer.parseInt(academy0304Request.getPaymentYear());

        StdntPaymentRecordMain stdntPaymentRecordMain = new StdntPaymentRecordMain();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if(academy0304Request.getPayMainId() != ""){
            stdntPaymentRecordMain = paymentRecordMainDao.findById(Integer.parseInt(academy0304Request.getPayMainId()));
        }else{
            stdntPaymentRecordMain.setCreateDate(timestamp);
        }
        stdntPaymentRecordMain.setRefStdntId(stdntId);
        stdntPaymentRecordMain.setGrade(grade);
        stdntPaymentRecordMain.setPaymentYear(year);
        stdntPaymentRecordMain.setPaymentMonth(paymentMonth);

        int mainId = paymentRecordMainDao.save(stdntPaymentRecordMain).getId();

        return mainId;
    }

    public void updatePayDateAndReceivingUnit(StdntPaymentRecordMain stdntPaymentRecordMain, String receivingUnit){

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        stdntPaymentRecordMain.setPayDate(timestamp);
        stdntPaymentRecordMain.setReceivingUnit(receivingUnit);

        paymentRecordMainDao.save(stdntPaymentRecordMain);

    }

    public List<StdntPaymentRecord> queryPaymentRecordByMainId(int paymentMainId){

        List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordDao.findByRefPaymentMainId(paymentMainId);

        return stdntPaymentRecordList;
    }

    public void interPaymentRecordCourseFee(int mainId, List<Academy0304Request_courseFeeList> academy0304RequestCourseFeeList){

        for(Academy0304Request_courseFeeList courseFeeList : academy0304RequestCourseFeeList){
            StdntPaymentRecord stdntPaymentRecord = new StdntPaymentRecord();

            if(courseFeeList.getPayRecordId() != ""){
                stdntPaymentRecord.setId(Integer.parseInt(courseFeeList.getPayRecordId()));
            }
            stdntPaymentRecord.setRefPaymentMainId(mainId);
            stdntPaymentRecord.setRefCourseFeeId(Integer.parseInt(courseFeeList.getCourseFeeId()));
            stdntPaymentRecord.setExpense(Integer.parseInt(courseFeeList.getExpense()));
            stdntPaymentRecord.setRemark(courseFeeList.getRemark());
            stdntPaymentRecord.setExpenseMonthStart(Integer.parseInt(courseFeeList.getExpenseMonthStart()));
            stdntPaymentRecord.setExpenseMonthEnd(Integer.parseInt(courseFeeList.getExpenseMonthEnd()));

            paymentRecordDao.save(stdntPaymentRecord);
        }

    }

    public List<Academy0301Response> queryStudentPaymentRecord(Academy0301Request academy0301Request) throws ParseException{

        List<Academy0301Response> academy0301ResponseList = new ArrayList<>();

        //query StudentInfo first if academy0301Request.getName() is not null.
        if(academy0301Request.getName() != ""){

            //use Name to find Student data.
            StudentInfo studentInfo = studentInfoDao.findByName(academy0301Request.getName()).get(0);

            //use stdntId and paymonth to find payment_Main.
            List<StdntPaymentRecordMain> stdntPaymentRecordMainList = this.findStdntPaymentRecordMainList(academy0301Request,
                    String.valueOf(studentInfo.getStdntId()));

            if( !stdntPaymentRecordMainList.isEmpty()){
                for(StdntPaymentRecordMain stdntPaymentRecordMain : stdntPaymentRecordMainList){
                    Academy0301Response academy0301Response = get0301ResponseDetail(stdntPaymentRecordMain, studentInfo);

                    academy0301ResponseList.add(academy0301Response);
                }
            }

        }else{
            //others query StdntPaymentRecordMain first.
            List<StdntPaymentRecordMain> stdntPaymentRecordMainList = this.findStdntPaymentRecordMainList(academy0301Request,
                    "");

            //use main.Id to find courseList.
            //use Name to find Student data.
            if( !stdntPaymentRecordMainList.isEmpty()){
                for(StdntPaymentRecordMain stdntPaymentRecordMain : stdntPaymentRecordMainList){
                    StudentInfo studentInfo = studentInfoDao.findByStdntId(stdntPaymentRecordMain.getRefStdntId());
                    Academy0301Response academy0301Response = get0301ResponseDetail(stdntPaymentRecordMain, studentInfo);

                    academy0301ResponseList.add(academy0301Response);
                }
            }
        }

        return academy0301ResponseList;

    }

    public Academy0301Response get0301ResponseDetail(StdntPaymentRecordMain stdntPaymentRecordMain, StudentInfo studentInfo){

        Academy0301Response academy0301Response = new Academy0301Response();

        List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordDao.findByRefPaymentMainId(stdntPaymentRecordMain.getId());

        if(!stdntPaymentRecordList.isEmpty()){

            academy0301Response.setStdntId(String.valueOf(studentInfo.getStdntId()));
            academy0301Response.setStdntName(studentInfo.getName());
            academy0301Response.setGrade(studentService.getGradeName(studentInfo.getGrade()));
            academy0301Response.setPayMainId(String.valueOf(stdntPaymentRecordMain.getId()));
            academy0301Response.setPaymentMonth(String.valueOf(stdntPaymentRecordMain.getPaymentMonth()));
            academy0301Response.setPaymentYear(String.valueOf(stdntPaymentRecordMain.getPaymentYear()));
            academy0301Response.setCourseFeeList(getPaymentCourseList(stdntPaymentRecordList));
            academy0301Response.setPaymentCrDate(stdntPaymentRecordMain.getCreateDate().toString().substring(0, 10));
            academy0301Response.setPayDate(stdntPaymentRecordMain.getPayDate() == null ? "" : stdntPaymentRecordMain.getPayDate().toString().substring(0, 10));
            academy0301Response.setReceivingUnit(stdntPaymentRecordMain.getReceivingUnit());

        }

        return academy0301Response;
    }

    //0301 動態查詢
    public List<StdntPaymentRecordMain> findStdntPaymentRecordMainList(Academy0301Request academy0301Request, String stdntId) throws ParseException{

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT *");
        sql.append("  FROM stdnt_payment_record_main");
        sql.append(" WHERE 1=1");

        String querySql = getQuerySql(sql, academy0301Request, stdntId);
        Query query = entityManager.createNativeQuery(querySql.toString(),StdntPaymentRecordMain.class);
        setQueryParameter(query, academy0301Request, stdntId);


        return query.getResultList();
    }

    private void setQueryParameter(Query query, Academy0301Request academy0301Request, String stdntId) throws ParseException {

        //年份
        if (StringUtils.isNotBlank(academy0301Request.getPaymentYear())) {
            query.setParameter("paymentYear", academy0301Request.getPaymentYear());
        }

        //月份
        if (StringUtils.isNotBlank(academy0301Request.getPaymentMonth())) {
            query.setParameter("paymentMonth", academy0301Request.getPaymentMonth());
        }

        //年級
        if (StringUtils.isNotBlank(academy0301Request.getGrade())) {

            List<Integer> grades = new ArrayList<>();
            int grade = Integer.parseInt(academy0301Request.getGrade());

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
            query.setParameter("grade", grades);
        }

        //學生ID
        if (StringUtils.isNotBlank(stdntId)) {
            query.setParameter("stdntId", stdntId);
        }
    }

    private String getQuerySql(StringBuilder sql, Academy0301Request academy0301Request, String stdntId) throws ParseException {

        //年份
        if (StringUtils.isNotBlank(academy0301Request.getPaymentYear())) {
            sql.append(" and PAYMENT_YEAR = :paymentYear");
        }

        //月份
        if (StringUtils.isNotBlank(academy0301Request.getPaymentMonth())) {
            sql.append(" and PAYMENT_MONTH = :paymentMonth");
        }

        //年級
        if (StringUtils.isNotBlank(academy0301Request.getGrade())) {
            sql.append(" and GRADE IN :grade");
        }

        //學生ID
        if (StringUtils.isNotBlank(stdntId)) {
            sql.append(" and REF_STDNT_ID = :stdntId");
        }

        sql.append(" ORDER BY GRADE, PAYMENT_MONTH DESC");

        return sql.toString();

    }

    public List<Academy0301Response_courseFeeList> getPaymentCourseList(List<StdntPaymentRecord> stdntPaymentRecordList){

        List<Academy0301Response_courseFeeList> courseFeeLists = new ArrayList<>();

        if(!stdntPaymentRecordList.isEmpty()){
            for(StdntPaymentRecord stdntPaymentRecord : stdntPaymentRecordList){

                Academy0301Response_courseFeeList courseFee = new Academy0301Response_courseFeeList();

                courseFee.setPayRecordId(String.valueOf(stdntPaymentRecord.getId()));
                courseFee.setCourseFeeId(String.valueOf(stdntPaymentRecord.getRefCourseFeeId()));
                courseFee.setRemark(stdntPaymentRecord.getRemark());
                courseFee.setExpense(String.valueOf(stdntPaymentRecord.getExpense()));
                courseFee.setExpenseMonthStart(String.valueOf(stdntPaymentRecord.getExpenseMonthStart()));
                courseFee.setExpenseMonthEnd(String.valueOf(stdntPaymentRecord.getExpenseMonthEnd()));

                courseFeeLists.add(courseFee);
            }
        }
        return courseFeeLists;
    }

}
