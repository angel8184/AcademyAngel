package com.academy.service;

import com.academy.dao.*;
import com.academy.model.Academy0501Request;
import com.academy.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

@Service
@Transactional
public class TotalSummaryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudentDao studentDao;
    @Autowired
    CourseFeeDao courseFeeDao;
    @Autowired
    SignUpRecordDao signUpRecordDao;
    @Autowired
    PaymentRecordMainDao paymentRecordMainDao;
    @Autowired
    PaymentRecordDao paymentRecordDao;
    @Autowired
    ReceiptService receiptService;

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

    public List<String> getExcelHeader(List<CourseFeeInfo> courseFeeInfoList){
        List<String> courseFeeNameList = new ArrayList<>();

        courseFeeNameList.add("學生姓名");
        courseFeeNameList.add("年級");
        courseFeeNameList.add("生日");
        courseFeeNameList.add("身分證字號");
        courseFeeNameList.add("家長姓名");
        courseFeeNameList.add("電話");
        courseFeeNameList.add("課程明細");


        for (CourseFeeInfo courseFeeInfo : courseFeeInfoList){
            String courseFeeName = courseFeeInfo.getName();
            courseFeeNameList.add(courseFeeName);
            logger.debug("courseFeeNameList:{}", courseFeeName);
        }

        courseFeeNameList.add("繳費合計金額");
        courseFeeNameList.add("收費日期");
        courseFeeNameList.add("收款單位");

        return courseFeeNameList;
    }

    public List<List<Object>> getStudentPayRecordList(Academy0501Request academy0501Request, List<CourseFeeInfo> courseFeeInfoList){

        //兜出每一位學生的資料
        //step1. use grade and paymentMonth to get stdntPaymentRecordMainList(get how many student pay for paymentMonth)
        //step2. get total courseFeeMap and courseFeeCount
        //step3. first loop stdntIdList to get studentInfo, signUpCourseFee and payRecordMap.
        //step4. second loop courseFeeCount to get courseFeeMap key to mapping payRecordMap.
        //step5. List<List<Object>> add student List.

        //主要塞回JXLS的datalist
        List<List<Object>> studentPayRecordList = new ArrayList<>();

        DecimalFormat expenseFormat = new DecimalFormat("#,##0");

        //step1. use grade and paymentMonth to get stdntPaymentRecordMainList
        List<StdntPaymentRecordMain> stdntPaymentRecordMainList = paymentRecordMainDao.findByGradeAndPaymentMonth(
                Integer.valueOf(academy0501Request.getGrade()), Integer.valueOf(academy0501Request.getMonth()));
        logger.debug("How many students in this condition:{}", stdntPaymentRecordMainList.size());

        //step2. get total courseFeeMap and courseFeeCount
        LinkedHashMap<Integer, String> courseFeeMap = new LinkedHashMap<>();

        for(CourseFeeInfo courseFeeInfo : courseFeeInfoList){
            courseFeeMap.put(courseFeeInfo.getId(), courseFeeInfo.getName());
        }

        //step3. first main loop stdntPaymentRecordMainList to get studentInfo, signUpCourseFee and payRecordMap.
        for(StdntPaymentRecordMain stdntPaymentRecordMain : stdntPaymentRecordMainList){

            //每一筆學生的總資料
            List<Object> studentPayRecord = new ArrayList<>();

            //繳費合計金額
            int totalAmount = 0;

            //studentInfo: "學生姓名", "年級", "生日", "身分證字號", "家長姓名", 電話"
            StudentInfo studentInfo = studentDao.findByStdntId(stdntPaymentRecordMain.getRefStdntId());
            studentPayRecord.add(studentInfo.getName());
            studentPayRecord.add(gradeMap.get(studentInfo.getGrade()));
            studentPayRecord.add(studentInfo.getBirth().toString().substring(0,10));
            studentPayRecord.add(studentInfo.getIdCard());
            studentPayRecord.add(studentInfo.getParentName());
            studentPayRecord.add(studentInfo.getPhone());

            //signUpCourseFee
            List<StdntSignUpRecord> stdntSignUpRecordList = signUpRecordDao.findByRefStdntId(stdntPaymentRecordMain.getRefStdntId());
            String signUpCourseFee = receiptService.getCourseFeeName(stdntSignUpRecordList);
            studentPayRecord.add(signUpCourseFee);

            //payRecordMap
            List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordDao.findByRefPaymentMainId(stdntPaymentRecordMain.getId());
            LinkedHashMap<Integer, Integer> payRecordMap = new LinkedHashMap<>();

            for(StdntPaymentRecord stdntPaymentRecord : stdntPaymentRecordList){
                payRecordMap.put(stdntPaymentRecord.getRefCourseFeeId(), stdntPaymentRecord.getExpense());
            }

            //step4. second main loop courseFeeCount to get courseFeeMap key to mapping payRecordMap.
            for (Map.Entry<Integer, String> courseFeeMapEntry : courseFeeMap.entrySet()){
                if(payRecordMap.get(courseFeeMapEntry.getKey()) == null){
                    studentPayRecord.add(0);
                }else{
                    totalAmount += payRecordMap.get(courseFeeMapEntry.getKey());
                    studentPayRecord.add(expenseFormat.format(payRecordMap.get(courseFeeMapEntry.getKey())));
                }
            }

            //add "繳費合計金額", "收費日期", "收款單位"
            studentPayRecord.add(expenseFormat.format(totalAmount));
            studentPayRecord.add(stdntPaymentRecordMain.getPayDate() == null ? "" : stdntPaymentRecordMain.getPayDate()
                    .toString().substring(0,10));
            studentPayRecord.add(stdntPaymentRecordMain.getReceivingUnit());

            //step5. List<List<Object>> add student List.
            studentPayRecordList.add(studentPayRecord);
        }


        return studentPayRecordList;
    }
}
