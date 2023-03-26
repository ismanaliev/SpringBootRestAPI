package com.peaksoft.service.impl;

import com.peaksoft.dto.CourseRequest;
import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.GroupResponse;
import com.peaksoft.dto.TeacherResponse;
import com.peaksoft.entity.*;
import com.peaksoft.repository.CompanyRepository;
import com.peaksoft.repository.CourseRepository;
import com.peaksoft.repository.GroupRepository;
import com.peaksoft.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final TeacherServiceImpl teacherService;
    private final GroupServiceImpl groupService;

    @Override
    public List<CourseResponse> getAllCourse() {
        List<Course>courses = courseRepository.findAll();
        List<CourseResponse>courseResponses = new ArrayList<>();
        for(Course course : courses){
            courseResponses.add(mapToResponse(course));
        }
        return courseResponses;
    }

    @Override
    public CourseResponse addCourse(CourseRequest course) {
        Course course1 = mapToEntity(course);
          courseRepository.save(course1);
        return mapToResponse(course1);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest course) {
        Course course1 = courseRepository.findById(id).get();
        course1.setCourseName(course.getCourseName());
        course1.setDurationMonth(course.getDurationMonth());
        if(course.getCompanyId() != null){
            Company company = companyRepository.findById(course.getCompanyId()).get();
            course1.setCompany(company);
        }
        courseRepository.saveAndFlush(course1);
        return mapToResponse(course1);
    }

    @Override
    public CourseResponse getById(Long id) {
        Course course = courseRepository.findById(id).get();
        return mapToResponse(course);
    }

    @Override
    public void deleteCourse(Long id) {
      Course course = courseRepository.findById(id).get();
      courseRepository.delete(course);
    }

    @Override
    public List<TeacherResponse> getTeachersByCourseId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        List<User>teachers = courseRepository.getTeachersByCourseId(id, pageable);
        List<TeacherResponse>teacherResponses = new ArrayList<>();
        for(User teacher : teachers){
            teacherResponses.add(teacherService.mapToResponse(teacher));
        }
        return teacherResponses;
    }

    @Override
    public List<GroupResponse> getGroupsByCourseId(Long id, int page, int size) {
        Pageable pageable =PageRequest.of(page-1, size);
        List<Group>groups = courseRepository.getGroupsByCourseId(id,pageable);
        List<GroupResponse>groupResponses = new ArrayList<>();
        for(Group group : groups){
            groupResponses.add(groupService.mapToResponse(group));
        }
        return groupResponses;
    }

    public Course mapToEntity (CourseRequest courseRequest){
        Course course = new Course();
        course.setCourseName(courseRequest.getCourseName());
        course.setDurationMonth(courseRequest.getDurationMonth());
        if(courseRequest.getCompanyId() != null){
            Company company = companyRepository.findById(courseRequest.getCompanyId()).get();
            course.setCompany(company);
        }
        course.setGroups(courseRequest.getGroups());
//        if(courseRequest.getUser().getRole() == Role.INSTRUCTOR){
//            course
//        }
        return course;
    }

    public CourseResponse mapToResponse (Course course){
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setCourseName(course.getCourseName());
        courseResponse.setDurationMonth(course.getDurationMonth());
        return courseResponse;
    }
}
