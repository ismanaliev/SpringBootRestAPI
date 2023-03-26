package com.peaksoft.service.impl;

import com.peaksoft.dto.CompanyRequest;
import com.peaksoft.dto.CompanyResponse;
import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.entity.Company;
import com.peaksoft.entity.Course;
import com.peaksoft.entity.Role;
import com.peaksoft.entity.User;
import com.peaksoft.repository.CompanyRepository;
import com.peaksoft.repository.CourseRepository;
import com.peaksoft.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final StudentServiceImpl studentService;
    private final CourseServiceImpl courseService;
    private final CourseRepository courseRepository;

    @Override
    public List<CompanyResponse> getAllCompany() {
        List<Company>companies = companyRepository.findAll();
        List<CompanyResponse>responses = new ArrayList<>();
        for(Company company : companies) {
            responses.add(mapToResponse(company));
        }
        return responses;
    }

    @Override
    public CompanyResponse save(CompanyRequest company) {
        Company company1 = mapToEntity(company);
        companyRepository.save(company1);
        return mapToResponse(company1);
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyRequest company) {
        Company company1 = companyRepository.findById(id).get();
        company1.setCompanyName(company.getCompanyName());
        company1.setLocatedCountry(company.getLocatedCountry());
        companyRepository.save(company1);
        return mapToResponse(company1);
    }

    @Override
    public CompanyResponse getById(Long id) {
        Company company = companyRepository.findById(id).get();
        return mapToResponse(company);
    }

    @Override
    public void deleteCompany(Long id) {
      Company company = companyRepository.findById(id).get();
      companyRepository.delete(company);
    }

    @Override
    public List<CourseResponse> getCoursesByCompanyId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        List<Course>companyCourses = companyRepository.getCoursesByCompanyId(id,pageable);
        List<CourseResponse>courseResponses = new ArrayList<>();
        for(Course course1 : companyCourses){
                courseResponses.add(courseService.mapToResponse(course1));
        }
        return courseResponses;
    }

    @Override
    public List<StudentResponse> getStudentsByCompanyId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        List<User>students = companyRepository.getStudentsByCompanyId(id,pageable);
        List<StudentResponse>studentResponses = new ArrayList<>();
        for(User student : students){
            if(student.getRole().equals(Role.STUDENT))
                studentResponses.add(studentService.mapToResponse(student));
        }

        return studentResponses;
    }

    private Company mapToEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setLocatedCountry(companyRequest.getLocatedCountry());
        return company;
    }

    private CompanyResponse mapToResponse(Company company){
        CompanyResponse response = new CompanyResponse();
        response.setId(company.getId());
        response.setCompanyName(company.getCompanyName());
        response.setLocatedCountry(company.getLocatedCountry());
        return response;
    }
}
