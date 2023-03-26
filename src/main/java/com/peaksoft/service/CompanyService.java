package com.peaksoft.service;

import com.peaksoft.dto.CompanyRequest;
import com.peaksoft.dto.CompanyResponse;
import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.entity.Company;
import com.peaksoft.entity.Course;


import java.util.List;

public interface CompanyService {
    List<CompanyResponse> getAllCompany();
    CompanyResponse save(CompanyRequest company);
    CompanyResponse updateCompany(Long id, CompanyRequest company);
    CompanyResponse getById(Long id);
    void deleteCompany(Long id);
    List<CourseResponse>getCoursesByCompanyId(Long id, int page, int size);
    List<StudentResponse>getStudentsByCompanyId(Long id, int page, int size);
}
