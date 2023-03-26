package com.peaksoft.service;

import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.dto.TeacherRequest;
import com.peaksoft.dto.TeacherResponse;
import com.peaksoft.entity.Course;

import java.util.List;

public interface TeacherService {
    List<TeacherResponse>getAllTeachers();
    TeacherResponse save( TeacherRequest teacher );
    TeacherResponse updateTeacher(Long id, TeacherRequest teacher);
    TeacherResponse getById(Long id);
    void deleteTeacher(Long id);
    List<CourseResponse> getCoursesByTeacherId(Long id, int page, int size);

    List<StudentResponse> quantityOfStudents(Long id, int page, int size);
}
