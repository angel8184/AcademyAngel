package com.academy.dao;

import com.academy.vo.StdntPaymentRecord;
import com.academy.vo.StdntPaymentRecordMain;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRecordMainDao extends CrudRepository<StdntPaymentRecordMain, Integer> {

    public List<StdntPaymentRecordMain> findAllByOrderByGradeDesc();

    public StdntPaymentRecordMain findByRefStdntIdAndPaymentMonth(int stdntId, int month);

    public StdntPaymentRecordMain findByRefStdntIdAndGradeAndPaymentMonth(int stdntId, int grade, int month);

    public StdntPaymentRecordMain findById(int mainId);

    public List<StdntPaymentRecordMain> findByRefStdntIdAndGradeOrderByPaymentMonthDesc(int stdntId, int grade);

    public List<StdntPaymentRecordMain> findByGrade(int grade);

    public List<StdntPaymentRecordMain> findByGradeAndPaymentMonth(int grade, int month);

    @Query("SELECT e FROM StdntPaymentRecordMain e WHERE e.grade IN :grades AND e.paymentMonth = :month ORDER BY e.grade")
    List<StdntPaymentRecordMain> findByGradesAndPaymentMonthOrderByGrade(@Param("grades")List<Integer> grades,
                                                             @Param("month") int month);
}
