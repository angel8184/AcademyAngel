package com.academy.service;

import com.academy.dao.PaymentRecordDao;
import com.academy.dao.PaymentRecordMainDao;
import com.academy.vo.StdntPaymentRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentRecordService {

    @Autowired
    PaymentRecordMainDao paymentRecordMainDao;
    @Autowired
    PaymentRecordDao paymentRecordDao;



    public List<StdntPaymentRecord> queryPaymentRecord(int stdntId){
        List<StdntPaymentRecord> stdntPaymentRecordList = paymentRecordDao.findByRefStdntId(stdntId);

        return stdntPaymentRecordList;
    }
}
