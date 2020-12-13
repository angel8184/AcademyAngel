package com.academy.dao;

import com.academy.vo.StudentInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDao extends CrudRepository<StudentInfo, Integer> {

    public List<StudentInfo> findAllByOrderByGradeDesc();

    public StudentInfo findByStdntIdAndGrade(int stdntId, int grade);

    public StudentInfo findByStdntId(int stdntId);

    public List<StudentInfo> findByGrade(int grade);

    public List<StudentInfo> findByName(String name);

    public List<StudentInfo> findByGradeAndName(int grade, String name);

    @Query("SELECT e FROM StudentInfo e WHERE e.grade IN :grades order by e.grade")     // 2. Spring JPA In cause using @Query
    List<StudentInfo> findByGrades(@Param("grades")List<Integer> grades);

}
