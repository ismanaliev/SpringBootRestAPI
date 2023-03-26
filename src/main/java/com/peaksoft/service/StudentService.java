package com.peaksoft.service;

import com.peaksoft.dto.StudentRequest;
import com.peaksoft.dto.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse>getAllStudents();
    StudentResponse save( StudentRequest student);
    StudentResponse update(Long id, StudentRequest student);
    StudentResponse getById(Long id);
    void deleteStudent(Long id);
    List<StudentResponse> search(String text, int page, int size);
}
