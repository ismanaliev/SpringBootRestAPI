package com.peaksoft.service.impl;


import com.peaksoft.dto.StudentRequest;
import com.peaksoft.dto.StudentResponse;
import com.peaksoft.entity.Group;
import com.peaksoft.entity.Role;
import com.peaksoft.entity.User;
import com.peaksoft.entity.studyFormat.StudyFormat;
import com.peaksoft.repository.GroupRepository;
import com.peaksoft.repository.UserRepository;
import com.peaksoft.service.StudentService;
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
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupRepository groupRepository;


    public User mapToEntity(StudentRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.STUDENT);
        user.setStudyFormat(StudyFormat.valueOf(request.getStudyFormat()));
        user.setCreatedDate(LocalDate.now());
        if(request.getGroupId() != null){
            Group group = groupRepository.findById(request.getGroupId()).get();
            user.setGroup(group);
        }
        return user;
    }
    public StudentResponse mapToResponse(User user){
        StudentResponse response = new StudentResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setStudyFormat(user.getStudyFormat());
        response.setRole(user.getRole());
        response.setCreated(user.getCreatedDate());
        return response;
    }

    @Override
    public List<StudentResponse> getAllStudents() {
        List<User>users = userRepository.findAll();
        List<StudentResponse>responses = new ArrayList<>();
        for (User user : users){
            if(user.getRole().equals(Role.STUDENT)){
                responses.add(mapToResponse(user));
            }
        }
        return responses;
    }

    @Override
    public StudentResponse save(StudentRequest student) {
        User user = mapToEntity(student);
        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest student) {
        User user = userRepository.findById(id).get();
        user.setFirstName(student.getFirstName());
        user.setLastName(student.getLastName());
        user.setEmail(student.getEmail());
        Group group = groupRepository.findById(student.getGroupId()).get();
        user.setGroup(group);
        user.setStudyFormat(StudyFormat.valueOf(student.getStudyFormat()));
        user.setPassword(passwordEncoder.encode(student.getPassword()));
        userRepository.saveAndFlush(user);
        return mapToResponse(user);
    }

    @Override
    public StudentResponse getById(Long id) {
        User user =  userRepository.findById(id).get();
        return mapToResponse(user);
    }

    @Override
    public void deleteStudent(Long id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }
    @Override
    public List<StudentResponse>search(String text, int page, int size){
        String text1 = text == null ? "" : text;
        Pageable pageable = PageRequest.of(page-1,size);
        List<User>students = userRepository.searchStudentsAndPagination(text1.toUpperCase(), pageable);
        List<StudentResponse>responses = new ArrayList<>();
        for (User student : students) {
            if(student.getRole().equals(Role.STUDENT)) {
                responses.add(mapToResponse(student));
            }
        }
        return responses;

    }


}
