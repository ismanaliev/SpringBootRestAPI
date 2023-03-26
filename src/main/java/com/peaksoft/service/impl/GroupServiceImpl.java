package com.peaksoft.service.impl;


import com.peaksoft.dto.*;
import com.peaksoft.entity.Company;
import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.User;
import com.peaksoft.repository.CompanyRepository;
import com.peaksoft.repository.CourseRepository;
import com.peaksoft.repository.GroupRepository;
import com.peaksoft.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final StudentServiceImpl studentService;

    @Override
    public List<GroupResponse> getAllGroups() {
        List<Group>groups = groupRepository.findAll();
        List<GroupResponse>groupResponses = new ArrayList<>();
        for(Group group : groups){
            groupResponses.add(mapToResponse(group));
        }
        return groupResponses;
    }

    @Override
    public GroupResponse addGroup(GroupRequest group) {
        Group group1 = mapToEntity(group);
        groupRepository.save(group1);
        return mapToResponse(group1);
    }

    @Override
    public GroupResponse updateGroup(Long id, GroupRequest group) {
        Group group1 = groupRepository.findById(id).get();
        group1.setGroupName(group.getGroupName());
        group1.setDateOfStart(group.getDateOfStart());
        group1.setDateOfFinish(group.getDateOfFinish());
        if(group.getCourseId() != null){
            Course course = courseRepository.findById(group.getCourseId()).get();
            List<Course> courses = new ArrayList<>();
            courses.add(course);
            group1.setCourse(courses);
        }
        groupRepository.saveAndFlush(group1);
        return mapToResponse(group1);
    }

    @Override
    public GroupResponse getById(Long id) {
        Group group = groupRepository.findById(id).get();
        return mapToResponse(group);
    }

    @Override
    public void deleteGroup(Long id) {
      Group group = groupRepository.findById(id).get();
      groupRepository.delete(group);
    }

    @Override
    public List<StudentResponse> getStudentsByGroupId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        List<User>students = groupRepository.getStudentsByGroupId(id,pageable);
        List<StudentResponse>studentResponses = new ArrayList<>();
        for(User student : students){
            studentResponses.add(studentService.mapToResponse(student));
        }
        return studentResponses;
    }

//    @Override
//    public List<CourseResponse> getCoursesByGroupId(Long id, int page, int size) {
//        Pageable pageable =PageRequest.of(page-1, size);
//        List<Course>courses = groupRepository.getCoursesByGroupId(id,pageable);
//        List<CourseResponse>courseResponses = new ArrayList<>();
//        for(Course course : courses){
//            courseResponses.add(mapToResponse(course));
//        }
//        return courseResponses;
//    }

    @Override
    public List<CourseResponse> getCoursesByGroupId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        List<Course>courses = groupRepository.getCoursesByGroupId(id,pageable);
        List<CourseResponse>courseResponses = new ArrayList<>();
        for(Course course : courses){
            courseResponses.add(mapToResponse(course));
        }
        return courseResponses;
    }

    @Override
    public List<StudentResponse> search(String studentName, int page, int size) {

        return null;
    }

    public Group mapToEntity (GroupRequest request){
        Group group = new Group();
        group.setGroupName(request.getGroupName());
        group.setDateOfStart(request.getDateOfStart());
        group.setDateOfFinish(request.getDateOfFinish());
        group.setCourseId(request.getCourseId());
        if(request.getCourseId() != null){
            Course course = courseRepository.findById(request.getCourseId()).get();
            List<Course> courses = new ArrayList<>();
            courses.add(course);
            group.setCourse(courses);
        }
        return group;
    }

    public GroupResponse mapToResponse( Group group ){
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(group.getId());
        groupResponse.setGroupName(group.getGroupName());
        groupResponse.setDateOfStart(group.getDateOfStart());
        groupResponse.setDateOfFinish(group.getDateOfFinish());
        return groupResponse;
    }
    public CourseResponse mapToResponse (Course course){
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setCourseName(course.getCourseName());
        courseResponse.setDurationMonth(course.getDurationMonth());
        return courseResponse;
    }
}
