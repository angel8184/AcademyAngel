package com.academy.dao;

import com.academy.vo.StdntPaymentRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRecordDao extends CrudRepository<StdntPaymentRecord, Integer> {

    public List<StdntPaymentRecord> findByRefPaymentMainId(int paymentMainId);

}
