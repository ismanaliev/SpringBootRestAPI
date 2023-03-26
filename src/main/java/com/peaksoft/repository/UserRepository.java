package com.peaksoft.repository;

import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.entity.Course;
import com.peaksoft.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select c from Course c join c.user t where t.id= :id")
    List<Course> getCourseByTeacherId(@Param("id") Long id, Pageable pageable);
    @Query("select s from User s join " +
            " s.group g join g.course c join c.user t where t.id= :id")
    List<User> getStudentsById(@Param("id") Long id, Pageable pageable);
    @Query("select s from User s where upper(s.firstName) like concat('%', :text, '%')" +
            " or upper(s.lastName) like concat('%', :text, '%')")
    List<User>searchStudentsAndPagination(@Param("text") String text, Pageable pageable);

    User findUserByEmail(String email);

    //User findUserByUsername(String name);

    //User findByUsername1(String name);
}
