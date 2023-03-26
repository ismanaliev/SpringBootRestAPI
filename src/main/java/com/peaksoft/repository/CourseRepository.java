package com.peaksoft.repository;

import com.peaksoft.dto.GroupResponse;
import com.peaksoft.dto.TeacherResponse;
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
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select t from User t join t.course course where course.id= :id")
    List<User> getTeachersByCourseId(@Param("id") Long id, Pageable pageable);

    @Query("select g from Group g join g.course course where course.id= :id")
    List<Group>getGroupsByCourseId(@Param("id") Long id, Pageable pageable);

}