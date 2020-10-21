package com.academy.dao;

import com.academy.vo.StdntSignUpRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignUpRecordDao extends CrudRepository<StdntSignUpRecord, Integer> {

    public List<StdntSignUpRecord> findByRefStdntId(int stdntId);
}
