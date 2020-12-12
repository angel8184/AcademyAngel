package com.academy.service;

import com.academy.dao.PaymentRecordDao;
import com.academy.dao.PaymentRecordMainDao;
import com.academy.model.Academy0304Request;
import com.academy.model.Academy0304Request_courseFeeList;
import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class PaymentRecordService {

    @Autowired
    PaymentRecordMainDao paymentRecordMainDao;
    @Autowired
    PaymentRecordDao paymentRecordDao;


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

    public int insertPaymentRecordMain(Academy0304Request academy0304Request){

        int stdntId = Integer.parseInt(academy0304Request.getStdntId());
        int grade = Integer.parseInt(academy0304Request.getGrade());
        int paymentMonth = Integer.parseInt(academy0304Request.getPaymentMonth());

        StdntPaymentRecordMain stdntPaymentRecordMain = new StdntPaymentRecordMain();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if(academy0304Request.getPayMainId() != ""){
            stdntPaymentRecordMain.setId(Integer.parseInt(academy0304Request.getPayMainId()));
        }
        stdntPaymentRecordMain.setRefStdntId(stdntId);
        stdntPaymentRecordMain.setGrade(grade);
        stdntPaymentRecordMain.setPaymentMonth(paymentMonth);
        stdntPaymentRecordMain.setCreateDate(timestamp);

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

}
