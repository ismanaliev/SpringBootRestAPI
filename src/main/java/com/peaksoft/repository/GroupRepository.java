package com.peaksoft.repository;

import com.peaksoft.dto.StudentResponse;
import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {
    @Query("select s from User s join s.group gr where gr.id= :id")
    List<User> getStudentsByGroupId(@Param("id") Long id, Pageable pageable);

    @Query("select c from Course c join c.groups gr1 where gr1.id= :id")
    List<Course>getCoursesByGroupId(@Param("id") Long id, Pageable pageable);

    @Query("select  s from User s  join s.group gr where gr.id= :groupId and s.firstName = :studentName")
    List<StudentResponse> search(@Param("studentName") String studentName, @Param("groupId") Long groupId);
}
