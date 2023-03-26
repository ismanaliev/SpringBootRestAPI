package com.peaksoft.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TeacherRequest {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Long courseId;
}
