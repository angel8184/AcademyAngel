package com.academy.dao;

import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRecordMainDao extends CrudRepository<StdntPaymentRecordMain, Integer> {


}
