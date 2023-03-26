package com.peaksoft.service.impl;


import com.peaksoft.dto.CourseResponse;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.dto.TeacherRequest;
import com.peaksoft.dto.TeacherResponse;
import com.peaksoft.entity.Course;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.Role;
import com.peaksoft.entity.User;
import com.peaksoft.repository.CourseRepository;
import com.peaksoft.repository.UserRepository;
import com.peaksoft.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;
//    private final GroupRepository groupRepository;
  //  private final CourseServiceImpl courseService;

    private final StudentServiceImpl studentService;

    @Override
    public List<TeacherResponse> getAllTeachers() {
        List<User>users = userRepository.findAll();
        List<TeacherResponse>responses = new ArrayList<>();
        for (User user : users){
            if(user.getRole().equals(Role.INSTRUCTOR)){
                responses.add(mapToResponse(user));
            }
        }
        return responses;
    }

    @Override
    public TeacherResponse save(TeacherRequest teacher) {
        User user = mapToEntity(teacher);
        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public TeacherResponse updateTeacher(Long id, TeacherRequest teacher) {
        User user = userRepository.findById(id).get();
        user.setFirstName(teacher.getFirstName());
        user.setEmail(teacher.getEmail());
        user.setLastName(teacher.getLastName());
        user.setPassword(passwordEncoder.encode(teacher.getPassword()));
        if(teacher.getCourseId() != null){
            Course course = courseRepository.findById(teacher.getCourseId()).get();
            user.setCourse(course);
        }
        userRepository.saveAndFlush(user);
        return mapToResponse(user);
    }

    @Override
    public TeacherResponse getById(Long id) {
        User user =  userRepository.findById(id).get();
        return mapToResponse(user);
    }

    @Override
    public void deleteTeacher(Long id) {
       User user =  userRepository.findById(id).get();
       userRepository.delete(user);
    }

    @Override
    public List<CourseResponse> getCoursesByTeacherId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        List<Course>courses = userRepository.getCourseByTeacherId(id,pageable);
        List<CourseResponse>courseResponse = new ArrayList<>();
        for(Course course1 : courses) {
            courseResponse.add(mapToResponse(course1));
        }
        return courseResponse;
    }

    @Override
    public List<StudentResponse> quantityOfStudents(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        List<User>students = userRepository.getStudentsById(id,pageable);
        List<StudentResponse>studentResponses = new ArrayList<>();
        for (User student : students) {
            if(student.getRole().equals(Role.STUDENT))
                studentResponses.add(studentService.mapToResponse(student));
        }
        return studentResponses;

    }

    private User mapToEntity(TeacherRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.INSTRUCTOR);
        user.setCreatedDate(LocalDate.now());
        if(request.getCourseId() != null){
            Course course = courseRepository.findById(request.getCourseId()).get();
            user.setCourse(course);
        }
        return user;
    }
    public TeacherResponse mapToResponse(User user){
        TeacherResponse response = new TeacherResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRole(user.getRole());
        response.setCreated(user.getCreatedDate());
        return response;
    }
    public CourseResponse mapToResponse (Course course){
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setCourseName(course.getCourseName());
        courseResponse.setDurationMonth(course.getDurationMonth());
        return courseResponse;
    }
}

