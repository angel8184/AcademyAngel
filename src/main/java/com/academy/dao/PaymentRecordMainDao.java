package com.academy.dao;

import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRecordMainDao extends CrudRepository<StdntPaymentRecordMain, Integer> {

    public List<StdntPaymentRecordMain> findAllByOrderByGradeDesc();

    public StdntPaymentRecordMain findByRefStdntIdAndPaymentMonth(int stdntId, int month);

    public StdntPaymentRecordMain findById(int mainId);

    public List<StdntPaymentRecordMain> findByRefStdntIdAndGradeOrderByPaymentMonthDesc(int stdntId, int grade);

    public List<StdntPaymentRecordMain> findByGrade(int grade);
}