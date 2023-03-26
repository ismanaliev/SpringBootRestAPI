package com.peaksoft.dto;

import com.peaksoft.entity.Group;
import com.peaksoft.entity.Role;
import com.peaksoft.entity.studyFormat.StudyFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter@Setter
public class StudentResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate created;
    private Role role;
    private StudyFormat studyFormat;
}
