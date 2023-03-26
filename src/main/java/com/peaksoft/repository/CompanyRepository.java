package com.peaksoft.repository;

import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.entity.Company;
import com.peaksoft.entity.Course;
import com.peaksoft.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    @Query(" select c from Course c join c.company com where com.id= :id")
    List<Course>getCoursesByCompanyId(@Param("id") Long id, Pageable pageable);

    @Query("select s from User s join " +
            " s.group g join g.course c join c.company com where com.id= :id")
    List<User> getStudentsByCompanyId(@Param("id") Long id, Pageable pageable);

}
