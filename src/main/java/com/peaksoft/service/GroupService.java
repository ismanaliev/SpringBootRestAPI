package com.peaksoft.service;

import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.GroupRequest;
import com.peaksoft.dto.GroupResponse;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.Course;

import java.util.List;

public interface GroupService {
    List<GroupResponse> getAllGroups();
    GroupResponse addGroup(GroupRequest group);
    GroupResponse updateGroup(Long id, GroupRequest group);
    GroupResponse getById(Long id);
    void deleteGroup(Long id);
    List<StudentResponse> getStudentsByGroupId(Long id, int page, int size);
    List<CourseResponse>getCoursesByGroupId(Long id, int page,int size);
    List<StudentResponse> search(String studentName,int page, int size);
}
