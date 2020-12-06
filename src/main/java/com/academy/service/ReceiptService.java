package com.academy.service;

import com.academy.model.CourseFeeDetail;
import com.academy.model.ReceiptMain;
import com.academy.vo.CourseFeeInfo;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntSignUpRecord;
import com.academy.vo.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReceiptService {

    @Autowired
    CourseFeeService courseFeeService;

    @Cacheable(key = "#root.methodName")
    public Map<Integer, String> getCourseFeeMap(){
        Map<Integer,String> courseFeeMap = new HashMap<Integer,String>();

        List<CourseFeeInfo> courseFeeInfoList = courseFeeService.queryAllCourseFee();
        for(CourseFeeInfo courseFeeInfo : courseFeeInfoList){
            courseFeeMap.put(courseFeeInfo.getId(), courseFeeInfo.getName());
        }
        return courseFeeMap;
    }

    public ReceiptMain fetchReciptMainFromData(StudentInfo studentInfo, List<StdntSignUpRecord> stdntSignUpRecordList,
                                               int year, int paymentMonth, List<StdntPaymentRecord> stdntPaymentRecordList){

        ReceiptMain receiptMain = new ReceiptMain();

        receiptMain.setYear(String.valueOf(year));
        receiptMain.setMonth(String.valueOf(paymentMonth));
        receiptMain.setStudentName(studentInfo.getName());
        receiptMain.setSignUpCourseFee(getCourseFeeName(stdntSignUpRecordList));
        receiptMain.setCourseFeeDetailList(getCourseFeeDetailList(stdntPaymentRecordList));

        return receiptMain;
    }

    public String getCourseFeeName(List<StdntSignUpRecord> stdntSignUpRecordList){

        StringBuffer courseFeeNameBuffer = new StringBuffer();

        for(StdntSignUpRecord stdntSignUpRecord : stdntSignUpRecordList){

            courseFeeNameBuffer.append(getCourseFeeMap().get(stdntSignUpRecord.getRefCourseFeeId()));
            courseFeeNameBuffer.append("+");

        }
        String courseFeeName = courseFeeNameBuffer.substring(0, courseFeeNameBuffer.length()-1);

        return courseFeeName;
    }

    public List<CourseFeeDetail> getCourseFeeDetailList(List<StdntPaymentRecord> stdntPaymentRecordList){
        List<CourseFeeDetail> courseFeeDetailList = new ArrayList<>();

        for(StdntPaymentRecord stdntPaymentRecord : stdntPaymentRecordList){

            CourseFeeDetail courseFeeDetail = new CourseFeeDetail();
            courseFeeDetail.setCourseFee(getCourseFeeMap().get(stdntPaymentRecord.getRefCourseFeeId()));
            courseFeeDetail.setExpense(stdntPaymentRecord.getExpense());
            courseFeeDetail.setRemark(stdntPaymentRecord.getRemark());

            courseFeeDetailList.add(courseFeeDetail);
        }

        int listSize = courseFeeDetailList.size();

        if(listSize < 10){
            for(int i=0; i < 10 - listSize; i++){
                courseFeeDetailList.add(new CourseFeeDetail());
            }
        }

        return courseFeeDetailList;
    }
}
